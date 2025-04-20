package co.edu.eafit.dis.pi2.cicd
package cli

import cats.effect.IO
import cats.syntax.all.*
import smithy4s.Timestamp

import domain.model.NonEmptyString
import service.TodoService

import java.util.UUID
import smithy.api.TimestampFormat

private[cli] final class CommandInterpreter(
  service: TodoService[IO]
):
  def runCommand(command: Command): IO[String] =
    command match
      case Command.Help =>
        IO.pure(
          """|The following commands are available:
             |list               -> List all TODOs
             |add                -> Adds a new TODO
             |complete <todoId>  -> Completes the given TODO
          """.stripMargin
        )

      case Command.List =>
        service.listTodos().map { listTodosResponse =>
          listTodosResponse.todos.view
            .map { todo =>
              s"|\t${todo.todoId}\t|\t${todo.status}\t|\t${todo.reminder}\t|"
            }
            .mkString(
              start = "TODOs:\n|\tId\t|\tStatus\t|\tReminder\t|\n",
              sep = "\n",
              end = "\n"
            )
        }

      case Command.Add =>
        val askReminder =
          prompt(msg = "Reminder").map { reminder =>
            Either.cond(
              test = reminder.nonEmpty,
              right = NonEmptyString(reminder),
              left = "Reminder can't be empty"
            )
          }

        val askDueTime =
          prompt(msg = "Due time").map { rawDueTime =>
            Timestamp
              .parse(rawDueTime, format = TimestampFormat.DATE_TIME)
              .toRight(
                left = s"'${rawDueTime}' is not a proper DateTime"
              )
          }

        (
          askReminder,
          askDueTime
        ).tupled.flatMap(
          _.traverseN(service.addTodo).map(
            _.map(todoId => s"Successfully created TODO with id: ${todoId}").merge
          )
        )

      case Command.Complete(todoId) =>
        IO(UUID.fromString(todoId)).attempt.flatMap {
          case Right(todoId) =>
            service.completeTodo(todoId).attempt.map {
              case Right(()) =>
                s"Successfully completed todo ${todoId}"

              case Left(ex) =>
                s"Error completing todo ${todoId}: ${ex.getMessage}"
            }

          case Left(ex) =>
            IO.pure(ex.getMessage)
        }

      case Command.Unknown(command) =>
        IO.pure(
          s"""|Unknown command: ${command}
              |Type help for information on available commands
           """.stripMargin
        )
  end runCommand
end CommandInterpreter

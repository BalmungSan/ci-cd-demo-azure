package co.edu.eafit.dis.pi2.cicd
package cli

import cats.effect.IO
import smithy4s.Timestamp

import domain.model.NonEmptyString
import service.TodoService

import java.util.UUID
import smithy.api.TimestampFormat

private[cli] final class CommandInterpreter(
  service: TodoService[IO]
):
  private val askReminder: IO[NonEmptyString] =
    prompt(msg = "Reminder").flatMap { reminder =>
      if reminder.nonEmpty then IO.pure(NonEmptyString(reminder))
      else IO.raiseError(IllegalArgumentException("Reminder can't be empty"))
    }

  private val askDueTime: IO[Timestamp] =
    prompt(msg = "Due time").flatMap { rawDueTime =>
      IO.fromOption(Timestamp.parse(rawDueTime, format = TimestampFormat.DATE_TIME))(
        orElse = IllegalArgumentException(s"'${rawDueTime}' is not a proper DateTime")
      )
    }

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
        for
          reminder <- askReminder
          dueTime <- askDueTime
          todoId <- service.addTodo(reminder, dueTime)
        yield s"Successfully created TODO: ${todoId}"

      case Command.Complete(todoId) =>
        for
          todoId <- IO(UUID.fromString(todoId))
          _ <- service.completeTodo(todoId)
        yield s"Successfully completed TODO: ${todoId}"

      case Command.Unknown(command) =>
        IO.pure(
          s"""|Unknown command: ${command}
              |Type help for information on available commands
           """.stripMargin
        )
  end runCommand
end CommandInterpreter

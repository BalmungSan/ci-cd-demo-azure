package co.edu.eafit.dis.pi2.cicd
package cli

import cats.effect.IO

import service.TodoService

import java.util.UUID

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
        IO.never

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

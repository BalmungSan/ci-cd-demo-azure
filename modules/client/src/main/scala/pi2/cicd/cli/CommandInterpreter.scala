package co.edu.eafit.dis.pi2.cicd
package cli

import cats.effect.IO

import service.TodoService

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
        IO.never

      case Command.Add =>
        IO.never

      case Command.Complete(todoId) =>
        IO.never

      case Command.Unknown(command) =>
        IO.pure(
          s"""|Unknown command: ${command}
              |Type help for information on available commands
           """.stripMargin
        )
  end runCommand
end CommandInterpreter

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
          """
              """
        )

      case Command.List =>
        IO.never

      case Command.Add =>
        IO.never

      case Command.Complete(todoId) =>
        IO.never

      case Command.Unknown(command) =>
        IO.pure(
          """
              """
        )
  end runCommand
end CommandInterpreter

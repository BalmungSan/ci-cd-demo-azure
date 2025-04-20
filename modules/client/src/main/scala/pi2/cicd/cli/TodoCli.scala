package co.edu.eafit.dis.pi2.cicd
package cli

import cats.effect.IO

import service.TodoService

object TodoCli:
  def make(
    service: TodoService[IO]
  ): IO[Unit] =
    val interpreter = CommandInterpreter(service)

    val askForCommand: IO[Command] =
      prompt(msg = "Please input a command").map(Command.parse)

    val welcomeMessage: IO[Unit] =
      IO.println("Welcome to the TODO App")

    val loop: IO[Unit] =
      askForCommand.flatMap(interpreter.runCommand).attempt.flatMap {
        case Right(result) => IO.println(result)
        case Left(ex)      => IO.println(s"Error: ${ex.getMessage}")
      }

    welcomeMessage >> loop.foreverM

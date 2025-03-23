package co.edu.eafit.dis.pi2.cicd

import cats.effect.IO
import cats.effect.IOApp

object Main extends IOApp.Simple:
  override val run: IO[Unit] =
    config.load.flatMap { todoAppConfig =>
      TodoApp
        .make(
          config = todoAppConfig
        )
        .evalTap { server =>
          IO.println(s"Server started in address: ${server.address}")
        }
        .useForever
    }
end Main

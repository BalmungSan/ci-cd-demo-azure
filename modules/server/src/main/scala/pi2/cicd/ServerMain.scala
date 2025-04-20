package co.edu.eafit.dis.pi2.cicd

import cats.effect.IO
import cats.effect.IOApp

object ServerMain extends IOApp.Simple:
  override val run: IO[Unit] =
    config.load.flatMap { todoAppConfig =>
      TodoServiceApp
        .make(
          config = todoAppConfig
        )
        .evalTap { server =>
          IO.println(s"Server started in address: ${server.address}")
        }
        .useForever
    }
end ServerMain

package co.edu.eafit.dis.pi2.cicd

import cats.effect.IO
import cats.effect.IOApp
import org.http4s.syntax.all.* // Provides the uri interpolator.

import config.TodoCliAppConfig

object ClientMain extends IOApp.Simple:
  override val run: IO[Unit] =
    TodoCliApp
      .make(
        config = TodoCliAppConfig(
          todoServiceUri = uri"https://todo-service.politemoss-18cdb303.westus3.azurecontainerapps.io"
        )
      )
      .useForever
end ClientMain

package co.edu.eafit.dis.pi2.cicd

import cats.effect.IO
import cats.effect.Resource

import config.TodoCliAppConfig

object TodoCliApp:
  def make(
    config: TodoCliAppConfig
  ): Resource[IO, Unit] =
    client.todo
      .make(
        uri = config.todoServiceUri
      )
      .evalMap(
        cli.TodoCli.make
      )

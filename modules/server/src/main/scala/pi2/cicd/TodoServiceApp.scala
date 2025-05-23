package co.edu.eafit.dis.pi2.cicd

import cats.effect.IO
import cats.effect.Resource
import org.http4s.server.Server

import config.TodoServiceAppConfig

object TodoServiceApp:
  def make(
    config: TodoServiceAppConfig
  ): Resource[IO, Server] =
    for
      skunkSession <- repository.SkunkSession.make(config = config.db)
      _ <- repository.migrations.run(session = skunkSession).toResource
      todoRepository = repository.TodoRepository.make(session = skunkSession)
      todoService = service.todo.make(repository = todoRepository)
      server <- server.make(
        config = config.server,
        service = todoService
      )
    yield server
  end make
end TodoServiceApp

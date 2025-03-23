package co.edu.eafit.dis.pi2.cicd

import cats.effect.IO
import cats.effect.Resource
import org.http4s.server.Server

import config.TodoAppConfig

object TodoApp:
  def make(
    config: TodoAppConfig
  ): Resource[IO, Server] =
    for
      skunkSession <- repository.SkunkSession.make(config = config.db)
      todoRepository = repository.TodoRepository.make(session = skunkSession)
      todoService = service.todo.make(repository = todoRepository)
      server <- server.make(
        config = config.server,
        service = todoService
      )
    yield server
  end make
end TodoApp

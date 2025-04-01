package co.edu.eafit.dis.pi2.cicd
package server

import cats.effect.IO
import cats.effect.Resource
import cats.syntax.all._
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.server.Server
import smithy4s.http4s.SimpleRestJsonBuilder
import smithy4s.http4s.swagger.{docs => SwaggerDocs}

import config.ServerConfig
import service.TodoService

def make(
  config: ServerConfig,
  service: TodoService[IO]
): Resource[IO, Server] =
  SimpleRestJsonBuilder.routes(service).resource.flatMap { todoRoutes =>
    val docsRoutes = SwaggerDocs[IO](TodoService)

    EmberServerBuilder
      .default[IO]
      .withHttp2
      .withHost(config.host)
      .withPort(config.port)
      .withHttpApp((todoRoutes <+> docsRoutes).orNotFound)
      .build
  }

package co.edu.eafit.dis.pi2.cicd
package server

import cats.effect.IO
import cats.effect.Resource
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.server.Server
import smithy4s.http4s.SimpleRestJsonBuilder

import config.ServerConfig
import service.TodoService

def make(
  config: ServerConfig,
  service: TodoService[IO]
): Resource[IO, Server] =
  SimpleRestJsonBuilder.routes(service).resource.flatMap { todoRoutes =>
    EmberServerBuilder
      .default[IO]
      .withHttp2
      .withHost(config.host)
      .withPort(config.port)
      .withHttpApp(todoRoutes.orNotFound)
      .build
  }

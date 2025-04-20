package co.edu.eafit.dis.pi2.cicd
package client
package todo

import cats.effect.IO
import cats.effect.Resource
import org.http4s.Uri
import org.http4s.ember.client.EmberClientBuilder
import smithy4s.http4s.SimpleRestJsonBuilder

import service.TodoService

def make(
  uri: Uri
): Resource[IO, TodoService[IO]] =
  EmberClientBuilder.default[IO].withHttp2.build.flatMap { client =>
    SimpleRestJsonBuilder(service = TodoService)
      .client(client)
      .uri(uri)
      .resource
  }

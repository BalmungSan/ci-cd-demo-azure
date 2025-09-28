package co.edu.eafit.dis.pi2.cicd
package repository

import cats.effect.IO
import cats.effect.Resource
import org.typelevel.otel4s.trace.Tracer
import skunk.Session

import config.DBConfig

object SkunkSession:
  def make(
    config: DBConfig
  ): Resource[IO, Session[IO]] =
    given Tracer[IO] = Tracer.noop
    for
      dbHost <- config.host.resolve[IO].toResource
      session <- Session
        .Builder[IO]
        .withHost(dbHost.toUriString)
        .withPort(config.port.value)
        .withDatabase(config.database)
        .withUserAndPassword(config.user, config.password)
        .withSSL(if config.sslEnabled then skunk.SSL.System else skunk.SSL.None)
        .single
    yield session
  end make
end SkunkSession

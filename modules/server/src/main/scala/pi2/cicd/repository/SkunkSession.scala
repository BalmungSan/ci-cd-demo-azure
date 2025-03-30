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
      session <- Session.single[IO](
        host = dbHost.toUriString,
        port = config.port.value,
        database = config.database,
        user = config.user,
        password = Some(config.password),
        ssl = if config.sslEnabled then skunk.SSL.System else skunk.SSL.None
      )
    yield session
  end make
end SkunkSession

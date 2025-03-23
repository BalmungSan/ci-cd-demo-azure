package co.edu.eafit.dis.pi2.cicd
package resources

import cats.effect.IO
import cats.effect.Resource
import com.comcast.ip4s.Host
import com.comcast.ip4s.Port
import org.http4s.server.Server

import config.{TodoAppConfig, ServerConfig}

object TodoAppResource:
  val make: Resource[IO, Server] =
    PostgreSQLResource.make.flatMap { dBConfig =>
      TodoApp.make(
        config = TodoAppConfig(
          db = dBConfig,
          server = ServerConfig(
            host = Host.fromString("localhost").get,
            port = Port.fromInt(8181).get
          )
        )
      )
    }
end TodoAppResource

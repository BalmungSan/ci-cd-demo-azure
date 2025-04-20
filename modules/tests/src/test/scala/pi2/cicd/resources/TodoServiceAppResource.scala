package co.edu.eafit.dis.pi2.cicd
package resources

import cats.effect.IO
import cats.effect.Resource
import com.comcast.ip4s.Host
import com.comcast.ip4s.Port
import org.http4s.server.Server

import config.TodoServiceAppConfig
import config.ServerConfig

object TodoServiceAppResource:
  val make: Resource[IO, Server] =
    PostgreSQLResource.make.flatMap { dBConfig =>
      TodoServiceApp.make(
        config = TodoServiceAppConfig(
          db = dBConfig,
          server = ServerConfig(
            host = Host.fromString("localhost").get,
            port = Port.fromInt(8181).get
          )
        )
      )
    }
end TodoServiceAppResource

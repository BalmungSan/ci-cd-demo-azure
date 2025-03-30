package co.edu.eafit.dis.pi2.cicd
package resources

import cats.effect.IO
import cats.effect.Resource
import com.comcast.ip4s.Host
import com.comcast.ip4s.Port
import com.dimafeng.testcontainers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName

import config.DBConfig

object PostgreSQLResource:
  private val database = "test_db"
  private val user = "test_user"
  private val password = "1234"

  val make: Resource[IO, DBConfig] =
    ContainerResource
      .make(
        container = PostgreSQLContainer(
          dockerImageNameOverride = DockerImageName.parse("postgres:16"),
          databaseName = database,
          username = user,
          password = password
        )
      )
      .map { postgreSQLContainer =>
        DBConfig(
          host = Host.fromString(postgreSQLContainer.host).get,
          port = Port.fromInt(postgreSQLContainer.firstMappedPort).get,
          database = database,
          user = user,
          password = password,
          sslEnabled = false
        )
      }
end PostgreSQLResource

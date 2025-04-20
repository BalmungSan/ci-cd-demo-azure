package co.edu.eafit.dis.pi2.cicd
package config

import cats.effect.IO
import cats.syntax.all._
import ciris.env
import ciris.http4s.given
import com.comcast.ip4s.Host
import com.comcast.ip4s.Port

final case class TodoServiceAppConfig(
  server: ServerConfig,
  db: DBConfig
)

final case class ServerConfig(
  host: Host,
  port: Port
)

final case class DBConfig(
  host: Host,
  port: Port,
  database: String,
  user: String,
  password: String,
  sslEnabled: Boolean
)

private val config =
  (
    (
      env(name = "SERVER_HOST").as[Host],
      env(name = "SERVER_PORT").as[Port]
    ).parMapN(ServerConfig.apply),
    (
      env(name = "DB_HOST").as[Host],
      env(name = "DB_PORT").as[Port],
      env(name = "DB_NAME").as[String],
      env(name = "DB_USER").as[String],
      env(name = "DB_PASSWORD").as[String],
      env(name = "DB_SSL_ENABLED").as[Boolean].default(false)
    ).parMapN(DBConfig.apply)
  ).parMapN(TodoServiceAppConfig.apply)

val load: IO[TodoServiceAppConfig] =
  config.load[IO]

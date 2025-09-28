package co.edu.eafit.dis.pi2.cicd
package repository
package migrations

import cats.effect.IO
import cats.effect.Resource
import dumbo.Dumbo
import dumbo.logging.Implicits.consolePrettyWithTimestamp
import skunk.Session

def run(
  session: Session[IO]
): IO[Unit] =
  Dumbo
    .withResourcesIn[IO]("db/migrations")
    .withSession(
      sessionResource = Resource.pure(session),
      validateOnMigrate = true
    )
    .runMigration
    .flatMap { result =>
      IO.println(
        s"Migration completed with ${result.migrationsExecuted} migrations"
      )
    }

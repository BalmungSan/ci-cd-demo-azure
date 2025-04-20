package co.edu.eafit.dis.pi2.cicd
package config

import org.http4s.Uri

final case class TodoCliAppConfig(
  todoServiceUri: Uri
)

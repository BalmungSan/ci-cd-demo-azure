package co.edu.eafit.dis.pi2.cicd
package repository
package model

import java.time.Instant
import java.util.UUID

final case class TodoData(
  todoId: UUID,
  reminder: String,
  dueTime: Instant,
  completionTime: Option[Instant]
)

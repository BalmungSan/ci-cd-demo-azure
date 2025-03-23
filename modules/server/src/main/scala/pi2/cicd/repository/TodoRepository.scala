package co.edu.eafit.dis.pi2.cicd
package repository

import model.TodoData

import cats.effect.{IO, Resource}
import skunk.Session

import java.util.UUID
import java.time.Instant

trait TodoRepository:
  def saveTodo(todo: TodoData): IO[Unit]
  def markTodoAsCompleted(todoId: UUID, completionTime: Instant): IO[Unit]
  def listTodos: IO[List[TodoData]]

object TodoRepository:
  def make(
    session: Session[IO]
  ): TodoRepository =
    ???
  end make
end TodoRepository

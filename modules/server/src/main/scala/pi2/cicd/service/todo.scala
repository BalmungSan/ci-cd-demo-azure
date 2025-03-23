package co.edu.eafit.dis.pi2.cicd
package service
package todo

import java.time.Instant
import java.util.UUID

import cats.effect.IO
import smithy4s.Timestamp

import domain.model.{NonEmptyString, Todo, TodoStatus}
import repository.TodoRepository
import repository.model.TodoData

extension (instant: Instant)
  private[todo] def asTimestamp: Timestamp =
    Timestamp.fromEpochMilli(instant.toEpochMilli)

def make(
  repository: TodoRepository
): TodoService[IO] =
  new TodoService[IO]:
    override def addTodo(
      reminder: NonEmptyString,
      dueTime: Timestamp
    ): IO[AddTodoOutput] =
      for
        todoId <- IO.randomUUID
        _ <- repository.saveTodo(
          todo = TodoData(
            todoId = todoId,
            reminder = reminder.value,
            dueTime = Instant.ofEpochMilli(dueTime.epochMilli),
            completionTime = None
          )
        )
      yield AddTodoOutput(todoId)

    override def completeTodo(
      todoId: UUID
    ): IO[Unit] =
      IO.realTimeInstant.flatMap { now =>
        repository.markTodoAsCompleted(
          todoId = todoId,
          completionTime = now
        )
      }

    override def listTodos(): IO[ListTodosOutput] =
      for
        now <- IO.realTimeInstant
        todosData <- repository.listTodos
        todos = todosData.map { todoData =>
          val todoStatus =
            todoData.completionTime.fold(
              ifEmpty =
                if (todoData.dueTime.isAfter(now)) then
                  TodoStatus.Pending(
                    dueTime = todoData.dueTime.asTimestamp
                  )
                else
                  TodoStatus.Overdue(
                    dueTime = todoData.dueTime.asTimestamp
                  )
            ) { todoCompletionTime =>
              TodoStatus.Completed(
                dueTime = todoData.dueTime.asTimestamp,
                completionTime = todoCompletionTime.asTimestamp
              )
            }

          Todo(
            todoId = todoData.todoId,
            reminder = NonEmptyString(todoData.reminder),
            status = todoStatus
          )
        }
      yield ListTodosOutput(todos)
end make

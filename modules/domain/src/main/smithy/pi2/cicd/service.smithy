$version: "2"

namespace co.edu.eafit.dis.pi2.cicd.service

use alloy#UUID
use alloy#simpleRestJson
use co.edu.eafit.dis.pi2.cicd.domain.model#HasDueTime
use co.edu.eafit.dis.pi2.cicd.domain.model#HasReminder
use co.edu.eafit.dis.pi2.cicd.domain.model#TodoNotFoundError
use co.edu.eafit.dis.pi2.cicd.domain.model#Todos

@simpleRestJson
service TodoService {
    version: "1.0.0"
    operations: [
        AddTodo
        CompleteTodo
        ListTodos
    ]
}

structure TodoBody with [HasReminder, HasDueTime] {}

@http(method: "POST", uri: "/add")
operation AddTodo {
    input: TodoBody

    output := {
        @required
        todoId: UUID
    }
}

@http(method: "POST", uri: "/complete/{todoId}")
operation CompleteTodo {
    input := {
        @required
        @httpLabel
        todoId: UUID
    }

    output: Unit

    errors: [
        TodoNotFoundError
    ]
}

@http(method: "GET", uri: "/list")
operation ListTodos {
    input: Unit

    output := {
        @required
        todos: Todos
    }
}

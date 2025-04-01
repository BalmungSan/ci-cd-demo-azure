$version: "2"

namespace co.edu.eafit.dis.pi2.cicd.domain.model

use alloy#UUID
use smithy4s.meta#adt

@length(min: 1)
string NonEmptyString

@mixin
structure HasReminder {
    @required
    reminder: NonEmptyString
}

structure Todo with [HasReminder] {
    @required
    todoId: UUID

    @required
    status: TodoStatus
}

list Todos {
    member: Todo
}

@adt
union TodoStatus {
    pending: Pending
    overdue: Overdue
    completed: Completed
}

@mixin
structure HasDueTime {
    @timestampFormat("date-time")
    @required
    dueTime: Timestamp
}

structure Pending with [HasDueTime] {}

structure Overdue with [HasDueTime] {}

structure Completed with [HasDueTime] {
    @required
    completionTime: Timestamp
}

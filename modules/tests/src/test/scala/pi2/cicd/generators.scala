package co.edu.eafit.dis.pi2.cicd
package generators

import java.time.Instant

import cats.Show
import org.scalacheck.Gen
import smithy4s.Timestamp

import domain.model.NonEmptyString

val reminder: Gen[NonEmptyString] =
  Gen.alphaNumStr.filter(_.nonEmpty).map(NonEmptyString.apply)

object dueDate:
  def between(startTime: Instant, endTime: Instant): Gen[Timestamp] =
    val epochSecondRange =
      Gen.chooseNum(
        minT = startTime.getEpochSecond + 10,
        maxT = endTime.getEpochSecond - 10
      )

    epochSecondRange.map(Timestamp.fromEpochSecond)
  end between
end dueDate

def addTodoData(dueDate: Gen[Timestamp]): Gen[AddTodoData] =
  Gen.zip(reminder, dueDate).map(AddTodoData.apply)

final case class AddTodoData(
  reminder: NonEmptyString,
  dueTime: Timestamp
)

object AddTodoData:
  given Show[AddTodoData] = Show.fromToString
end AddTodoData

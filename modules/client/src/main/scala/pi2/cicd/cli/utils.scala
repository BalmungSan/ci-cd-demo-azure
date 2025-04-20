package co.edu.eafit.dis.pi2.cicd
package cli

import cats.effect.IO

private[cli] def prompt(msg: String): IO[String] =
  IO.print(msg) >> IO.print("> ") >> IO.readLine

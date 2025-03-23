package co.edu.eafit.dis.pi2.cicd
package resources

import cats.effect.IO
import cats.effect.Resource
import com.dimafeng.testcontainers.Container

object ContainerResource:
  def make[C <: Container](
    container: => C
  ): Resource[IO, C] =
    Resource.fromAutoCloseable(IO(container)).evalTap { container =>
      IO.blocking(container.start())
    }
  end make
end ContainerResource

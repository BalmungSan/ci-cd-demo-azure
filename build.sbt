import smithy4s.codegen.Smithy4sCodegenPlugin

// Dependencies versions.
val catsVersion = "2.13.0"
val catsEffectVersion = "3.5.7"
val fs2Version = "3.11.0"
val http4sVersion = "0.23.30"
val cirisVersion = "3.7.0"
val skunkVersion = "1.0.0-M10"
val dumboVersion = "0.5.5"
val testcontainersVersion = "0.43.0"
val weaverVersion = "0.8.4"

// Global settings.
ThisBuild / scalaVersion := "3.3.5"
ThisBuild / organization := "co.edu.eafit.dis"

// Common settings.
lazy val commonSettings = Seq(
  // Ensure we publish an artifact linked to the appropriate Java std library.
  scalacOptions += "-java-output-version:21",
  // Make all warnings verbose.
  scalacOptions += "-Wconf:any:verbose",
  // Base dependencies.
  libraryDependencies ++= Seq(
    "org.typelevel" %% "cats-core" % catsVersion,
    "org.typelevel" %% "cats-effect" % catsEffectVersion,
    "co.fs2" %% "fs2-core" % fs2Version,
    "co.fs2" %% "fs2-io" % fs2Version
  )
)

// Assembly merge strategy.
ThisBuild / assemblyMergeStrategy := {
  case PathList("META-INF", "smithy", _*) =>
    MergeStrategy.concat

  case x =>
    val oldStrategy = (ThisBuild / assemblyMergeStrategy).value
    oldStrategy(x)
}

lazy val domain =
  project
    .in(file("modules/domain"))
    .enablePlugins(Smithy4sCodegenPlugin)
    .settings(commonSettings)
    .settings(
      libraryDependencies ++= Seq(
        "org.http4s" %% "http4s-core" % http4sVersion,
        "com.disneystreaming.smithy4s" %% "smithy4s-core" % smithy4sVersion.value,
        "com.disneystreaming.smithy4s" %% "smithy4s-json" % smithy4sVersion.value,
        "com.disneystreaming.smithy4s" %% "smithy4s-http4s" % smithy4sVersion.value
      )
    )

lazy val server =
  project
    .in(file("modules/server"))
    .dependsOn(domain)
    .settings(commonSettings)
    .settings(
      run / fork := true,
      assembly / assemblyJarName := "todo-service.jar",
      libraryDependencies ++= Seq(
        "is.cir" %% "ciris" % cirisVersion,
        "is.cir" %% "ciris-http4s" % cirisVersion,
        "org.tpolecat" %% "skunk-core" % skunkVersion,
        "dev.rolang" %% "dumbo" % dumboVersion,
        "org.http4s" %% "http4s-server" % http4sVersion,
        "org.http4s" %% "http4s-ember-server" % http4sVersion
      )
    )

lazy val tests =
  project
    .in(file("modules/tests"))
    .dependsOn(server)
    .settings(commonSettings)
    .settings(
      libraryDependencies ++= Seq(
        "com.disneystreaming" %% "weaver-cats" % weaverVersion,
        "com.disneystreaming" %% "weaver-scalacheck" % weaverVersion,
        "com.dimafeng" %% "testcontainers-scala-core" % testcontainersVersion,
        "com.dimafeng" %% "testcontainers-scala-postgresql" % testcontainersVersion,
        "org.http4s" %% "http4s-client" % http4sVersion,
        "org.http4s" %% "http4s-ember-client" % http4sVersion
      ).map(_ % Test),
      testFrameworks += new TestFramework("weaver.framework.CatsEffect"),
      Test / fork := true
    )

lazy val root =
  project
    .in(file("."))
    .aggregate(
      domain,
      server,
      tests
    )

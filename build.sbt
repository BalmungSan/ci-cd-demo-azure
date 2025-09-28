import smithy4s.codegen.Smithy4sCodegenPlugin

// Dependencies versions.
val catsVersion = "2.13.0"
val catsEffectVersion = "3.6.3"
val fs2Version = "3.12.2"
val http4sVersion = "0.23.32"
val cirisVersion = "3.11.0"
val skunkVersion = "1.0.0-M11"
val dumboVersion = "0.6.0"
val testcontainersVersion = "0.43.0"
val weaverVersion = "0.10.1"

// Global settings.
ThisBuild / scalaVersion := "3.3.6"
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
  ),
  // Enable Scalafix in CI.
  semanticdbEnabled := true,
  semanticdbVersion := scalafixSemanticdb.revision
)

// Assembly merge strategy.
ThisBuild / assemblyMergeStrategy := {
  case PathList("META-INF", "smithy", _*) =>
    MergeStrategy.concat

  case path if path.endsWith("module-info.class") =>
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
        "com.disneystreaming.smithy4s" %% "smithy4s-http4s" % smithy4sVersion.value,
        "com.disneystreaming.smithy4s" %% "smithy4s-http4s-swagger" % smithy4sVersion.value
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

lazy val client =
  project
    .in(file("modules/client"))
    .dependsOn(domain)
    .settings(commonSettings)
    .settings(
      libraryDependencies ++= Seq(
        "org.http4s" %% "http4s-client" % http4sVersion,
        "org.http4s" %% "http4s-ember-client" % http4sVersion
      )
    )

lazy val tests =
  project
    .in(file("modules/tests"))
    .dependsOn(server, client)
    .settings(commonSettings)
    .settings(
      libraryDependencies ++= Seq(
        "org.typelevel" %% "weaver-cats" % weaverVersion,
        "org.typelevel" %% "weaver-scalacheck" % weaverVersion,
        "com.dimafeng" %% "testcontainers-scala-core" % testcontainersVersion,
        "com.dimafeng" %% "testcontainers-scala-postgresql" % testcontainersVersion
      ).map(_ % Test),
      testFrameworks += TestFrameworks.WeaverTestCats,
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

addCommandAlias(
  name = "formatAll",
  value = List(
    "scalafmtAll",
    "scalafmtSbt",
    "scalafixAll"
  ).mkString(";")
)
addCommandAlias(
  name = "formatCheck",
  value = List(
    "scalafmtCheckAll",
    "scalafmtSbtCheck",
    "scalafixAll --check"
  ).mkString(";")
)
addCommandAlias(
  name = "validate",
  value = List(
    "clean",
    "Test/compile",
    "formatCheck",
    "tests/test"
  ).mkString(";")
)

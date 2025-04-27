package co.edu.eafit.dis.pi2.cicd
package cli

private[cli] enum Command:
  case Help
  case List
  case Add
  case Edit(todoId: String)
  case Complete(todoId: String)
  case Unknown(command: String)

private[cli] object Command:
  def parse(command: String): Command =
    command.trim.toLowerCase match
      case "help"                => Help
      case "list"                => List
      case "add"                 => Add
      case s"edit ${todoId}"     => Edit(todoId = todoId)
      case s"complete ${todoId}" => Complete(todoId = todoId)
      case unknown               => Unknown(command = unknown)
  end parse
end Command

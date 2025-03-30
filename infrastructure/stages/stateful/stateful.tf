locals {
  db_user = "todo_service_user"
}

module "todo_service_db" {
  source = "../../modules/db"

  location            = var.location
  resource_group_name = var.resource_group_name

  db_server_name = var.db_server_name

  db_user     = local.db_user
  db_password = var.db_password
}

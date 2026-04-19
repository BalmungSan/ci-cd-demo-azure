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

module "todo_service" {
  source = "../../modules/service"

  location            = var.location
  resource_group_name = var.resource_group_name

  container_registry_login_server = var.service_container_registry_login_server
  container_registry_pull_user    = var.service_container_registry_pull_user
  service_image_tag               = var.service_image_tag

  exposed_port = 8080

  db_host     = module.todo_service_db.db_host
  db_port     = 5432
  db_name     = module.todo_service_db.db_name
  db_user     = local.db_user
  db_password = var.db_password
}

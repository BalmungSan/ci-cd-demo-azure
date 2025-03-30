module "todo_service_prereqs" {
  source = "../prereqs"

  location            = var.location
  resource_group_name = var.resource_group_name

  registry_name = var.registry_name
}

module "todo_service_stateful" {
  source = "../stateful"

  location            = var.location
  resource_group_name = var.resource_group_name

  db_server_name = var.db_server_name
  db_password    = var.db_password
}

module "todo_service" {
  source = "../../modules/service"

  location            = var.location
  resource_group_name = var.resource_group_name

  container_registry_login_server = module.todo_service_prereqs.todo_service_container_registry_login_server
  container_registry_pull_user    = module.todo_service_prereqs.todo_service_container_registry_pull_user
  service_image_tag               = var.service_image_tag

  exposed_port = 8080

  db_host     = module.todo_service_stateful.todo_service_db_host
  db_port     = module.todo_service_stateful.todo_service_db_port
  db_name     = module.todo_service_stateful.todo_service_db_name
  db_user     = module.todo_service_stateful.todo_service_db_user
  db_password = var.db_password
}

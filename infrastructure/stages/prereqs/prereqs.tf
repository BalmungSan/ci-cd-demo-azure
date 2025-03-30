module "todo_service_container_registry" {
  source = "../../modules/container_registry"

  location            = var.location
  resource_group_name = var.resource_group_name

  registry_name = var.registry_name
}

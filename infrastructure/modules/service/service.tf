module "rg" {
  source = "${path.root}/modules/resource_group"
}

resource "azurerm_container_app_environment" "this" {
  name                       = var.service_name
  location                   = rg.location
  resource_group_name        = rg.name
}

resource "azurerm_container_app" "this" {
  name                         = var.service_name
  container_app_environment_id = azurerm_container_app_environment.this.id
  location                     = rg.location
  resource_group_name          = rg.name
  revision_mode                = "Single"

  template {
    container {
      name   = var.service_name
      image  = var.service_image
      cpu    = 0.25
      memory = "0.5Gi"
    }
  }

  registry {
    server   = var.container_registry_login_server
    identity = "system"
  }
}

locals {
  service_name = "todo-service"
}

resource "azurerm_container_app_environment" "this" {
  name                = local.service_name
  location            = var.location
  resource_group_name = var.resource_group_name
}

resource "azurerm_container_app" "this" {
  name                         = local.service_name
  container_app_environment_id = azurerm_container_app_environment.this.id
  resource_group_name          = var.resource_group_name

  revision_mode = "Single"

  identity {
    type         = "UserAssigned"
    identity_ids = [var.container_registry_pull_user]
  }

  registry {
    server   = var.container_registry_login_server
    identity = var.container_registry_pull_user
  }

  template {
    container {
      name   = local.service_name
      image  = "${var.container_registry_login_server}/${local.service_name}:${var.service_image_tag}"
      cpu    = 0.25
      memory = "0.5Gi"
      env {
        name  = "SERVER_HOST"
        value = "0.0.0.0"
      }
      env {
        name  = "SERVER_PORT"
        value = var.exposed_port
      }
      env {
        name  = "DB_HOST"
        value = var.db_host
      }
      env {
        name  = "DB_PORT"
        value = var.db_port
      }
      env {
        name  = "DB_NAME"
        value = var.db_name
      }
      env {
        name  = "DB_USER"
        value = var.db_user
      }
      env {
        name  = "DB_PASSWORD"
        value = var.db_password
      }
      env {
        name  = "DB_SSL_ENABLED"
        value = true
      }
    }
  }

  ingress {
    allow_insecure_connections = true
    external_enabled           = true
    transport                  = "http2"
    target_port                = var.exposed_port
    traffic_weight {
      percentage      = 100
      latest_revision = true
    }
  }
}

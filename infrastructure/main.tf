terraform {
  required_providers {
    azurerm = {
      source  = "hashicorp/azurerm"
      version = "4.25.0"
    }
  }

  backend "azurerm" {
    use_azuread_auth = true
    container_name   = "ci-cd-demo-azure-iac"
    key              = "terraform.tfstate"
  }

  required_version = ">= 1.11.0"
}

provider "azurerm" {
  features {}
}

resource "azurerm_resource_group" "this" {
  name     = "ci-cd-demo-azure-rg"
  location = "East US 2"
}

module "todo_app" {
  source = "./stages/app"

  location            = azurerm_resource_group.this.location
  resource_group_name = azurerm_resource_group.this.name

  registry_name     = var.registry_name
  service_image_tag = var.service_image_tag
  db_server_name    = var.db_server_name
  db_password       = var.db_password
}

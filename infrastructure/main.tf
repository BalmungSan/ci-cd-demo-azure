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

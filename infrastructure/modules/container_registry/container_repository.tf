resource "azurerm_container_registry" "this" {
  name                = var.registry_name
  location            = var.location
  resource_group_name = var.resource_group_name
  sku                 = "Standard"
}

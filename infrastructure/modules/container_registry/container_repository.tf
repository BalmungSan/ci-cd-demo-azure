resource "azurerm_container_registry" "this" {
  name                = var.registry_name
  location            = var.location
  resource_group_name = var.resource_group_name
  sku                 = "Standard"
}

resource "azurerm_user_assigned_identity" "pull_user" {
  name                = "${var.registry_name}-pull-user"
  location            = var.location
  resource_group_name = var.resource_group_name
}

resource "azurerm_role_assignment" "pull_user" {
  principal_id         = azurerm_user_assigned_identity.pull_user.principal_id
  scope                = azurerm_container_registry.this.id
  role_definition_name = "acrpull"
  depends_on = [
    azurerm_user_assigned_identity.pull_user
  ]
}

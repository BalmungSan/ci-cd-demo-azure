module "rg" {
  source = "${path.root}/modules/resource_group"
}

resource "azurerm_container_registry" "this" {
  name                = vars.repository_name
  resource_group_name = rg.name
  location            = rg.location
  sku                 = "Standard"
}

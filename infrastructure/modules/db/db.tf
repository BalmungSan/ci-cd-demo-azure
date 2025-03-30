resource "azurerm_postgresql_flexible_server" "this" {
  name                = var.db_server_name
  location            = var.location
  resource_group_name = var.resource_group_name

  version           = "16"
  sku_name          = "B_Standard_B1ms"
  storage_tier      = "P4"
  storage_mb        = 32768
  auto_grow_enabled = false

  administrator_login    = var.db_user
  administrator_password = var.db_password

  public_network_access_enabled = false
}

resource "azurerm_postgresql_flexible_server_database" "this" {
  name      = "todo_db"
  server_id = azurerm_postgresql_flexible_server.this.id
  charset   = "UTF8"
  collation = "en_US.utf8"
}

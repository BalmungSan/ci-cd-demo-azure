output "db_host" {
  value = azurerm_postgresql_flexible_server.this.fqdn
}

output "db_name" {
  value = azurerm_postgresql_flexible_server_database.this.name
}

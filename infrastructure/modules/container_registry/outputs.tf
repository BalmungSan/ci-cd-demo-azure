output "login_server" {
  value = azurerm_container_registry.this.login_server
}

output "pull_user" {
  value = azurerm_user_assigned_identity.pull_user.id
}

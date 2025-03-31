output "service_host" {
  value = azurerm_container_app.this.ingress[0].fqdn
}

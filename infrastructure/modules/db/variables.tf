variable "location" {
  type        = string
  description = "The Azure Location in which to deploy the resources"
}

variable "resource_group_name" {
  type        = string
  description = "The name of the Azure Resource Group which will be used to group all resources together"
}

variable "db_server_name" {
  type        = string
  description = "The name of the Azure PostgreSQL server"
}

variable "db_user" {
  type        = string
  description = "The name of the database user"
}

variable "db_password" {
  type        = string
  sensitive   = true
  description = "The password for the database user"
}

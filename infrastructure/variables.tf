variable "registry_name" {
  type        = string
  description = "The name of the Azure Container Registry"
}

variable "db_server_name" {
  type        = string
  description = "The name of the Azure PostgreSQL server"
}

variable "service_image_tag" {
  type        = string
  description = "The Docker image tag to use for the service"
}

variable "db_password" {
  type        = string
  sensitive   = true
  description = "The password for the database user"
}

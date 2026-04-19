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

variable "db_password" {
  type        = string
  sensitive   = true
  description = "The password for the database user"
}

variable "service_container_registry_login_server" {
  type        = string
  description = "The login server of the Azure container registry that contains the service Docker images"
}

variable "service_container_registry_pull_user" {
  type        = string
  description = "The Azure user-assigned manged identity that is capable of pulling Docker images from the container registry"
}

variable "service_image_tag" {
  type        = string
  description = "The Docker image tag to use for the service"
}

variable "location" {
  type        = string
  description = "The Azure Location in which to deploy the resources"
}

variable "resource_group_name" {
  type        = string
  description = "The name of the Azure Resource Group which will be used to group all resources together"
}

variable "container_registry_login_server" {
  type        = string
  description = "The URL of the container registry login server"
}

variable "container_registry_pull_user" {
  type        = string
  description = "The id of the Azure user-assigned managed identity which can pull from the container registry"
}

variable "service_image_tag" {
  type        = string
  description = "The Docker image tag to use for the service"
}

variable "exposed_port" {
  type        = number
  description = "The port on which the application should be exposed"
}

variable "db_host" {
  type        = string
  description = "The host of the database to which the service will connect to"
}

variable "db_port" {
  type        = number
  description = "The port of the database to which the service will connect to"
}

variable "db_name" {
  type        = string
  description = "The name of the database to which the service will connect to"
}

variable "db_user" {
  type        = string
  description = "The user that the service will use to connect to the database"
}

variable "db_password" {
  type        = string
  sensitive   = true
  description = "The password that the service will use to connect to the database"
}

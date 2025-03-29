variable "service_name" {
  type = string
  description = "The name of the service / container"
}

variable "service_image" {
  type = string
  description = "The Docker image to use for the service"
}

variable "container_registry_login_server" {
  type = string
  description = "The URL of the container registry login server"
}

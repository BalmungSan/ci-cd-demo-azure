variable "location" {
  type        = string
  description = "The Azure Location in which to deploy the resources"
}

variable "resource_group_name" {
  type        = string
  description = "The name of the Azure Resource Group which will be used to group all resources together"
}

variable "registry_name" {
  type        = string
  description = "The name of the Azure Container Registry"
}

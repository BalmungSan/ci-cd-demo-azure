output "todo_service_db_host" {
  value = module.todo_service_db.db_host
}

output "todo_service_db_port" {
  value = 5432
}

output "todo_service_db_name" {
  value = module.todo_service_db.db_name
}

output "todo_service_db_user" {
  value = local.db_user
}

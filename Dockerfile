# Base Docker image.
FROM mcr.microsoft.com/openjdk/jdk:21-distroless

# Copy the assembly JAR into the app directory.
WORKDIR /home/app
COPY modules/server/target/scala-3.3.6/todo-service.jar app.jar

# Use the custom app user to run the application.
USER app

# RUN the application as the Docker entrypoint.
CMD ["-server", "-jar", "app.jar"]

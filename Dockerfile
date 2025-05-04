# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the packaged Spring Boot application JAR file into the container
# Replace 'book-service-0.0.1-SNAPSHOT.jar' with the actual name of your built JAR file
COPY target/checkout-return-service-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the Book Service application runs on
# Replace 8081 with the actual server.port configured in your book-service's application.properties
EXPOSE 8084

# Define the command to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
# Use an official Java runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
ADD /target/auth-service-0.0.1-SNAPSHOT.jar auth-service.jar


# Expose the port your app runs on
EXPOSE 8081

# Run the JAR file
CMD ["java", "-jar", "auth-service.jar"]
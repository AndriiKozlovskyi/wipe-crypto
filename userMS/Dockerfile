# Use an official Java runtime as a parent image
FROM openjdk:17-jdk-slim

WORKDIR /app

# Set the working directory in the container
ADD /target/userMS-1.0-SNAPSHOT.jar userMs1.jar


# Expose the port your app runs on
EXPOSE 8080

# Run the JAR file
CMD ["java", "-jar", "userMs1.jar"]
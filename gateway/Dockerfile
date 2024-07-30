# Use an official Java runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
ADD /target/gateway-0.0.1-SNAPSHOT.jar gateway.jar


# Expose the port your app runs on
EXPOSE 8082

# Run the JAR file
CMD ["java", "-jar", "gateway.jar"]
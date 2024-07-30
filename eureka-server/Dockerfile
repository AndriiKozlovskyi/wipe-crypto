# Use an official Java runtime as a parent image
FROM openjdk:17-jdk-slim
# Set the working directory in the container
ADD /target/eureka-server-0.0.1-SNAPSHOT.jar eureka-server.jar


# Expose the port your app runs on
EXPOSE 8761

# Run the JAR file
CMD ["java", "-jar", "eureka-server.jar"]
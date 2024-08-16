# Use an official Java runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
ADD /target/taskMS-1.0-SNAPSHOT.jar taskMS.jar


# Expose the port your app runs on
EXPOSE 8084

# Run the JAR file
CMD ["java", "-jar", "taskMS.jar"]
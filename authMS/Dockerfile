# Use an official Java runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
ADD /target/authMS-1.0-SNAPSHOT.jar authMS.jar


# Expose the port your app runs on
EXPOSE 8081

# Run the JAR file
CMD ["java", "-jar", "authMS.jar"]
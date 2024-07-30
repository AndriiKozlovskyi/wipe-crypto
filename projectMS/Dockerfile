# Use an official Java runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
ADD /target/projectMS-1.0-SNAPSHOT.jar projectMS.jar


# Expose the port your app runs on
EXPOSE 8083

# Run the JAR file
CMD ["java", "-jar", "projectMS.jar"]
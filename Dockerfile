# Use OpenJDK 21 base image
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file into the container
COPY target/Ecommerce-0.0.1-SNAPSHOT.jar Ecommerce-0.0.1-SNAPSHOT.jar

# Expose port 8080
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-jar", "Ecommerce-0.0.1-SNAPSHOT.jar"]

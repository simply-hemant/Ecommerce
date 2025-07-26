# Stage 1: Build the application
FROM maven:3.8.4-openjdk-17 AS build

WORKDIR /app

# Copy source code
COPY . .

# Build the application
RUN mvn clean package -DskipTests


# Stage 2: Run the application
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy jar from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port (match your application port)
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]

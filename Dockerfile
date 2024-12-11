# Stage 1: Build the application
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copy the pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the source code and build the application
COPY src ./src
COPY .env* ./
RUN mvn clean package -DskipTests

# Stage 2: Create the final image
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Create non-root user
RUN addgroup -S spring && adduser -S spring -G spring

# Copy the built jar file and configs
COPY --from=build /app/target/*.jar app.jar
COPY --from=build /app/.env* ./

# Set ownership
RUN chown -R spring:spring /app

# Switch to non-root user
USER spring:spring

# Health check
HEALTHCHECK --interval=30s --timeout=3s \
    CMD ["wget", "-q", "--spider", "http://localhost:8080/actuator/health"]

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
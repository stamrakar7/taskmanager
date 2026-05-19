# ================================================
# Task Manager — Dockerfile
# Spring Boot 3.2.0 + Java 17 + Maven 3.9.11
# ================================================

# ================================================
# STAGE 1 — BUILD
# Uses Maven to compile and package the app
# ================================================
FROM maven:3.9.11-eclipse-temurin-17 AS build
WORKDIR /app

# Copy pom.xml first for dependency caching
# If pom.xml not changed — skips download!
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build jar — skip tests
RUN mvn clean package -DskipTests

# ================================================
# STAGE 2 — RUN
# Small image — only what is needed to run
# ================================================
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy only the jar from Stage 1
COPY --from=build /app/target/*.jar app.jar

# Spring Boot port
EXPOSE 8080

# Run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]
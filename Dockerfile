# Build stage
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
# Download dependencies first to speed up subsequent builds
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn package -DskipTests

# Run stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENV DB_HOST=db \
    DB_PORT=5432 \
    DB_NAME=dental_db \
    DB_USER=postgres \
    DB_PASSWORD=dental_postgres_pass
ENTRYPOINT ["java", "-jar", "app.jar"]

FROM maven:3.6.1-jdk-11-slim AS build
WORKDIR /home/producer

# Fetch all dependencies
COPY pom.xml .
RUN mvn verify -DskipTests --fail-never

# Build application
COPY src ./src
RUN mvn verify

FROM openjdk:11-jre-slim
ENTRYPOINT ["java", "-jar", "/app/producer.jar"]

WORKDIR /app
COPY --from=build /home/producer/target .
FROM maven:3.8.1-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml ./
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar ./command-service.jar

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "command-service.jar"]

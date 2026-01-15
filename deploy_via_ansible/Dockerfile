FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /spring-app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:24-rc-oracle
MAINTAINER Kyriakos Kardabikis
WORKDIR /app
COPY --from=builder /spring-app/target/*.jar ./application.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "application.jar"]
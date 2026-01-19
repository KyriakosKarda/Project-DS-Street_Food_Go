FROM openjdk:24-rc-oracle

COPY .mvn .mvn
COPY src ./src
COPY mvnw pom.xml ./

RUN chmod +x mvnw

EXPOSE 8081
ENTRYPOINT ["./mvnw", "spring-boot:run"]
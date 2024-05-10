FROM eclipse-temurin:21.0.2_13-jdk-alpine
WORKDIR /app
COPY target/*.jar /client.jar
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "/client.jar"]

FROM eclipse-temurin:21.0.2_13-jdk-alpine
WORKDIR /app
COPY target/*.jar /transaction-processor.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "-Ddb.hostname=banking-db", "/transaction-processor.jar"]

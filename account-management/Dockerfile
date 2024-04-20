FROM eclipse-temurin:21.0.2_13-jdk-alpine
WORKDIR /app
COPY target/*.jar /account-management.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "-Ddb.hostname=banking-db", "/account-management.jar"]

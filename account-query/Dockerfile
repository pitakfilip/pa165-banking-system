FROM eclipse-temurin:21.0.2_13-jdk-alpine
WORKDIR /app
COPY target/*.jar /account-query.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "-Ddb.hostname=banking-db", "/account-query.jar"]

FROM eclipse-temurin:21.0.2_13-jdk-alpine
WORKDIR /app
COPY target/*.jar /transaction-processor.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "-Ddb.hostname=banking-db", "-Dbanking.apps.query.host=account-query", "-Dbanking.apps.management.host=account-management", "/transaction-processor.jar"]

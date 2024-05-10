 # PA165 Banking System

<p>
The Bank Management System is an application that offers a solution for managing bank accounts, transactions, and customer information. 
The system allows customers to access their accounts online, check their account balances, transfer, withdraw and deposit funds,
view transaction history and manage their account information. Users can also setup scheduled periodical payments. 
Each user account will have an associated currency. If there is an incoming transaction in a different currency, 
the system will automatically exchange the currencies. The system also provides a dashboard for bank employees to manage 
customer accounts and monitor all customers bank transactions. The system also provides a statistical module for employees, 
which can report total and average (per account) transactions (deposits, withdrawals, outgoing and incoming payments) in a selected date range.
</p>

## Run Apps
In order to start the applications please follow the following instructions:
1. Run `./.scripts/build_artifacts.sh`
2. Run `./.scripts/build_images.sh`
3. Run `docker-compose up -d` 

This builds all the required maven artifacts in the correct order, creates images on your local machine and then runs the specified
docker-compose file.

## Artifacts and Applications

| Name                  | Type       | URL                                                                         | Description                                                                                                                                                                                           |
|-----------------------|------------|-----------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| M2M-Banking-API       | library    | N/A                                                                         | Consists of OpenAPI contracts of microservices for a centralized distribution                                                                                                                         |
| Infrastructure        | library    | N/A                                                                         | Common implementations including exceptions, exception handling, spring security and other                                                                                                            |
| Account-Management    | app        | [Swagger-UI](http://localhost:8080/swagger-ui/index.html)<br>App port: 8080 | Spring application for management of banking users and accounts                                                                                                                                       |
| Account-Query         | app        | [Swagger-UI](http://localhost:8081/swagger-ui/index.html)<br>App port: 8081 | Spring application for querying current data on user accounts, containing a history of transactions, current balance and an employee API for reports                                                  |
| Transaction-Processor | app        | [Swagger-UI](http://localhost:8082/swagger-ui/index.html)<br>App port: 8082 | Spring application for executing transaction requests, automatic execution of scheduled payments and provides employee API process management and manual trigger of scheduled payments                |
| Client                | app        | [UI](http://localhost:8084)<br>App port: 8084                               | Spring application for logging into MUNI OICD and evaluating access token                                                                                                                             |
| PostgreSQL            | DB         | JDBC Connection String: `jdbc:postgresql://localhost:5432/banking`          | Singular DB for all microservices. Instead of having separate DB instances per spring application, each application has its own defined user and schema (each user has privileges to a single scheme) |
| Prometheus            | monitoring | [Prometheus UI](http://localhost:9000)                                      | Monitoring toolkit for metrics collection                                                                                                                                                             |
| Grafana               | monitoring | [Grafana UI](http://localhost:3000)                                         | Monitoring toolkit for metrics visualization and persistence                                                                                                                                          |
| Locust                | testing    | [Locust UI](http://localhost:8089)                                          | Load testing tool simulating user interactions for evaluating performance                                                                                                                             |

## Clearing and Seeding DB
Within the [DB docker config files](./.docker/db/scripts) there are two scripts that may be executed on your maching (if you have psql installed!)
or via the docker container of the database itself.

- For clearing the DB data please use [this bash script](./.docker/db/scripts/clear_db.sh), alternatively within the DB container call `./scripts/clear_db.sh`
- For seeding the DB with valid data please use [this bash script](./.docker/db/scripts/seed_db.sh), alternatively within the DB container call `./scripts/seed_db.sh`

## Use case diagram
<img src="./.documentation/useCaseDiagram.png" width="800">

## Module Architecture
Each service is implemented as a separate maven artifact consisting of a Spring-boot application. Within each project
we used the principles of `Hexagonal architecture`, where the domain itself consists of only pure Java classes,
with no technological dependencies. 

This architecture is ensured by creating separate packages `application` and `domain`. The application package contains technology specific
implementations for Spring and other, while the domain package contains the business logic of the given microservice.

By using such separation, we nicely created an extra separated layer on top of the
traditional `Controller, Facade, Service and Repository` layers. The main benefit of such separation is visible mainly when 
extending new features, implementation of tests and usage of custom abstractions with ease.
<br><br>
For a better visual reference of the described structure, refer to [Transaction Processor](./transaction-processor) 
where the [implementation](./transaction-processor/src/main/java/cz/muni/pa165/banking/) with the separation of domain logic creates a nicely visible hierarchy of responsibility.

## Technological dependencies
- Maven
- Java - OpenJDK21
- PostreSQL
- Docker
- OpenShift or Hashicorp Consul (still discussing which of the two to use for service registration and load-balancing)
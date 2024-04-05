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

## Use case diagram
<div style="width: 100%; display: flex; justify-content: center;">
    <img src="./useCaseDiagram.png" width="750">
</div>

<br>

## Module Architecture
Each service is implemented as a separate maven artifact consisting of a Spring-boot application. Within each project
we used the principles of `Hexagonal architecture` and `DDD`, where the domain itself consists of only pure Java classes,
with no technological dependencies. By using such separation, we nicely created an extra separated layer on top of the
traditional `Controller, Facade, Service and Repository` layers. The main benefit of such separation is visible mainly when 
extending new features, implementation of tests and usage of custom abstractions with ease.


## Technological dependencies
- Maven
- Java - OpenJDK21
- PostreSQL
- RabbitMq
- Docker
- OpenShift or Hashicorp Consul (still discussing which of the two to use for service registration and load-balancing)
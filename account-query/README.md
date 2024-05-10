# Account query service

<p>This service allows to view current balance of an account and 
transaction history of an account from some date range.
It also provides bank 
employees an ability to monitor all customers bank transactions.
The service also provides a statistical module (for employees), which can 
report total and average (per account) transactions (deposits, withdrawals, 
outgoing and incoming payments) in a selected date range.</p>

## API's

1. **Customer Service Api:**
    - Manages user-related operations such as getting current balance of account and getting past transactions.

2. **System Service Api:**
    - Handles system-related operations such as creation of new balance and adding transactions.

3. **Employee Service Api:**
    - Handles operations employee can call such as getting of all transactions and report for user.

## Architecture

![diagram](diagram.png)

<p>This service is composed of classes as can be seen on the diagram, 
in repository there are balances of accounts stored. Every balance class has
current balance stored in itself together with a list of transactions which 
led to this balance. Transaction consist of amount of money, transaction type,
 time when it was stored, and processID created by transaction processor service. 

This service allows also creation of reports, which are computed from issued transactions.
 Report class consists of six transactionStatistics classes, one for each type of
 transaction and one for all transactions together. In this transactionStatistics 
class, there is the total amount incoming/outgoing to/from account stored, with how many
 times there was incoming/outgoing transaction.</p>




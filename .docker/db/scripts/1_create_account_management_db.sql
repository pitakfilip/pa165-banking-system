SET ROLE "ACC_USER";
SET SEARCH_PATH TO bank_user;

-- CREATE TABLES

CREATE TABLE IF NOT EXISTS bank_user
(
    id DECIMAL PRIMARY KEY,
    email VARCHAR(50),
    password VARCHAR(50),
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    user_type VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS bank_account
(
    id                 DECIMAL PRIMARY KEY,
    number             VARCHAR(50),
    user_id            DECIMAL,
    max_spending_limit DECIMAL,
    type               VARCHAR(50),
    currency           VARCHAR(50)
);


CREATE TABLE IF NOT EXISTS scheduled_payment
(
    id                  DECIMAL PRIMARY KEY,
    sender_account_id   DECIMAL,
    receiver_account_id DECIMAL,
    amount              DECIMAL
);

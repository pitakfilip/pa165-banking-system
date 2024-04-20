SET ROLE "ACC_USER";
SET SEARCH_PATH TO bank_user;

-- CREATE TABLES

CREATE TABLE IF NOT EXISTS bank_user
(
    id BIGINT PRIMARY KEY,
    email VARCHAR(50),
    password VARCHAR(50),
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    user_type VARCHAR(50)
);
CREATE SEQUENCE bank_user_seq START 1 INCREMENT 50;

CREATE TABLE IF NOT EXISTS bank_account
(
    id                 BIGINT PRIMARY KEY,
    number             VARCHAR(50),
    user_id            DECIMAL,
    max_spending_limit DECIMAL,
    type               VARCHAR(50),
    currency           VARCHAR(50)
);
CREATE SEQUENCE bank_account_seq START 1 INCREMENT 50;

CREATE TABLE IF NOT EXISTS scheduled_payment
(
    id                     BIGINT PRIMARY KEY,
    source_account_id      BIGINT,
    target_account_id      BIGINT,
    amount                 DECIMAL,
    recurrence_type        VARCHAR(50),
    recurrence_payment_day INTEGER
);
CREATE SEQUENCE scheduled_payment_seq START 1 INCREMENT 50;
SET ROLE "ACC_ACCOUNT";
SET SEARCH_PATH TO bank_account;

-- CREATE TABLES

CREATE TABLE IF NOT EXISTS accounts
(
    id DECIMAL PRIMARY KEY,
    number VARCHAR(50),
    user_id DECIMAL,
    max_spending_limit DECIMAL,
    type VARCHAR(50),
    currency VARCHAR(50)
    );

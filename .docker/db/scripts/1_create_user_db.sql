SET ROLE "ACC_USER";
SET SEARCH_PATH TO bank_user;

-- CREATE TABLES

CREATE TABLE IF NOT EXISTS users
(
    id DECIMAL PRIMARY KEY,
    email VARCHAR(50),
    password VARCHAR(50),
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    user_type VARCHAR(50)
    );

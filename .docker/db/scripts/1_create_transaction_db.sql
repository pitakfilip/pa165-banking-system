SET ROLE "ACC_TRANSACTION";
SET SEARCH_PATH TO bank_transaction;

-- CREATE TABLES

CREATE TABLE IF NOT EXISTS proc_transaction
(
    trans_uuid UUID PRIMARY KEY,
    acc_source VARCHAR(255),
    acc_target VARCHAR(255),
    type       VARCHAR(255),
    amount     DECIMAL,
    curr_code  VARCHAR(255),
    detail_msg VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS proc_reg
(
    proc_uuid   UUID PRIMARY KEY,
    status_when TIMESTAMP,
    status      VARCHAR(255),
    status_info VARCHAR(9999)
);
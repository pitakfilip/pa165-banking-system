SET ROLE "ACC_SCHEDULED_PAYMENTS";
SET SEARCH_PATH TO scheduled_payment;

-- CREATE TABLES

CREATE TABLE IF NOT EXISTS scheduled_payments
(
    id DECIMAL PRIMARY KEY,
    sender_account_id DECIMAL,
    receiver_account_id DECIMAL,
    amount DECIMAL
    );

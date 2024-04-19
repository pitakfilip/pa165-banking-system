SET SEARCH_PATH TO scheduled_payment;

-- INSERT starting data

INSERT INTO scheduled_payments (id, sender_account_id, receiver_account_id, amount)
VALUES
    (1, 8, 9, 100.00),
    (2, 8, 3, 100.00),
    (3, 5, 6, 500.00),
    (4, 2, 1, 200.00),
    (5, 2, 5, 150.00);



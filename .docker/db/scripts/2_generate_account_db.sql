SET SEARCH_PATH TO bank_account;

-- INSERT starting data

INSERT INTO accounts (acc_id, acc_number, user_id, max_spending_limit, acc_type)
VALUES
    (1, '111111111', 1, 5000.00, 'SPENDING'),
    (2, '222222222', 1, 10000.00, 'SAVING'),
    (3, '333333333', 2, 2000.00, 'CREDIT'),
    (4, '444444444', 5, 7000.00, 'SPENDING'),
    (5, '555555555', 5, 15000.00, 'SAVING'),
    (6, '666666666', 6, 3000.00, 'CREDIT'),
    (7, '777777777', 3, 13000.00, 'CREDIT'),
    (8, '888888888', 7, 20000.00, 'CREDIT'),
    (9, '999999999', 8, 1000.00, 'CREDIT');

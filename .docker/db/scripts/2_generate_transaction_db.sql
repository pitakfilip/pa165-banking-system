SET SEARCH_PATH TO bank_transaction;

-- INSERT starting data

INSERT INTO proc_transaction (trans_uuid, acc_source, acc_target, type, amount, curr_code, detail_msg)
VALUES ('00000000-0000-0000-0000-000000000001', '123456789', '987654321', 'TRANSFER', 100.00, 'USD', 'Dummy transfer 1'),
       ('00000000-0000-0000-0000-000000000002', '987654321', '123456789', 'DEPOSIT', 200.00, 'EUR', 'Dummy deposit 1'),
       ('00000000-0000-0000-0000-000000000003', '111111111', '222222222', 'WITHDRAWAL', 300.00, 'GBP', 'Dummy withdrawal 1'),
       ('00000000-0000-0000-0000-000000000004', '333333333', '444444444', 'TRANSFER', 400.00, 'CAD', 'Dummy transfer 2'),
       ('00000000-0000-0000-0000-000000000005', '555555555', '666666666', 'DEPOSIT', 500.00, 'AUD', 'Dummy deposit 2');

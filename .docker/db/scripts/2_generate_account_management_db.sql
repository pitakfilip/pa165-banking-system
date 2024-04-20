SET SEARCH_PATH TO bank_user;

-- INSERT starting data

INSERT INTO bank_user (id, email, password, first_name, last_name, user_type)
VALUES
    (991, 'regular1@example.com', 'password1', 'John', 'Doe', 0),
    (992, 'regular2@example.com', 'password2', 'Jane', 'Smith', 0),
    (993, 'employee1@example.com', 'password3', 'David', 'Johnson', 1),
    (994, 'employee2@example.com', 'password4', 'Emily', 'Brown', 1),
    (995, 'regular3@example.com', 'password5', 'Michael', 'Williams', 0),
    (996, 'regular4@example.com', 'password6', 'Emma', 'Wilson', 0),
    (997, 'employee3@example.com', 'password7', 'Daniel', 'Martinez', 1),
    (998, 'employee4@example.com', 'password8', 'Olivia', 'Garcia', 1);

INSERT INTO bank_account (id, number, user_id, max_spending_limit, type, currency)
VALUES (3656018305580485508, 'a5dc3241-71c9-4594-8a07-083c9c2b7b1c', 1, 1000, 0, 'CZK'),
       (992, 'ACC2', 1, 1000, 1, 'CZK'),
       (993, 'ACC3', 1, 1000, 1, 'CZK'),
       (994, 'ACC4', 1, 1000, 1, 'CZK');


INSERT INTO scheduled_payment (id, source_account_id, target_account_id, amount, recurrence_type, recurrence_payment_day)
VALUES
    (991, 2, 3, 100, 0, 2),
    (992, 2, 3, 200, 0, 3),
    (993, 3, 2, 1200, 1, 15);



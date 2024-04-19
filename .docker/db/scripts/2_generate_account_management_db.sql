SET SEARCH_PATH TO bank_user;

-- INSERT starting data

INSERT INTO bank_user (id, email, password, first_name, last_name, user_type)
VALUES
    (1, 'regular1@example.com', 'password1', 'John', 'Doe', 'REGULAR'),
    (2, 'regular2@example.com', 'password2', 'Jane', 'Smith', 'REGULAR'),
    (3, 'employee1@example.com', 'password3', 'David', 'Johnson', 'EMPLOYEE'),
    (4, 'employee2@example.com', 'password4', 'Emily', 'Brown', 'EMPLOYEE'),
    (5, 'regular3@example.com', 'password5', 'Michael', 'Williams', 'REGULAR'),
    (6, 'regular4@example.com', 'password6', 'Emma', 'Wilson', 'REGULAR'),
    (7, 'employee3@example.com', 'password7', 'Daniel', 'Martinez', 'EMPLOYEE'),
    (8, 'employee4@example.com', 'password8', 'Olivia', 'Garcia', 'EMPLOYEE');

INSERT INTO bank_account (id, number, user_id, max_spending_limit, type, currency)
VALUES (3656018305580485508, 'a5dc3241-71c9-4594-8a07-083c9c2b7b1c', 1, 1000, 0, 'CZK'),
       (2, 'ACC2', 1, 1000, 1, 'CZK'),
       (3, 'ACC3', 1, 1000, 1, 'CZK'),
       (4, 'ACC4', 1, 1000, 1, 'CZK');


INSERT INTO scheduled_payment (id, sender_account_id, receiver_account_id, amount)
VALUES
    (1, 8, 9, 100.00),
    (2, 8, 3, 100.00),
    (3, 5, 6, 500.00),
    (4, 2, 1, 200.00),
    (5, 2, 5, 150.00);



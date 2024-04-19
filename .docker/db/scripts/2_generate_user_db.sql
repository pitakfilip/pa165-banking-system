SET SEARCH_PATH TO bank_user;

-- INSERT starting data

INSERT INTO users (user_id, email, password, first_name, last_name, user_type)
VALUES
    (1, 'regular1@example.com', 'password1', 'John', 'Doe', 'REGULAR'),
    (2, 'regular2@example.com', 'password2', 'Jane', 'Smith', 'REGULAR'),
    (3, 'employee1@example.com', 'password3', 'David', 'Johnson', 'EMPLOYEE'),
    (4, 'employee2@example.com', 'password4', 'Emily', 'Brown', 'EMPLOYEE', '{}'),
    (5, 'regular3@example.com', 'password5', 'Michael', 'Williams', 'REGULAR'),
    (6, 'regular4@example.com', 'password6', 'Emma', 'Wilson', 'REGULAR'),
    (7, 'employee3@example.com', 'password7', 'Daniel', 'Martinez', 'EMPLOYEE'),
    (8, 'employee4@example.com', 'password8', 'Olivia', 'Garcia', 'EMPLOYEE');



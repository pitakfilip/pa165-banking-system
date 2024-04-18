SET SEARCH_PATH TO bank_account;

-- INSERT starting data
INSERT INTO balance VALUES
                                   (90.00, 'id1'),
                                   (118.00, 'id2'),
                                   (243.00, 'id3'),
                                   (-243.00, 'id4');

INSERT INTO bal_transaction VALUES
                                       (90.00, 1, TIMESTAMP WITH TIME ZONE '2024-04-15 14:11:00.288446+02', 1, UUID 'b775b79f-52a9-45c4-8ab3-bdb9f0f3d4ae', 'id1'),
                                       (94.00, 2, TIMESTAMP WITH TIME ZONE '2024-04-15 14:11:42.919109+02', 2, UUID 'c91b6ef6-32ac-4160-bf7f-97979ca84606', 'id2'),
                                       (24.00, 1, TIMESTAMP WITH TIME ZONE '2024-04-15 14:11:57.480291+02', 3, UUID '6a1fa40e-3ba8-46a8-b005-8bcd63ae44de', 'id2'),
                                       (243.00, 3, TIMESTAMP WITH TIME ZONE '2024-04-15 14:12:14.949613+02', 4, UUID 'd9974137-7000-4f34-8c64-1d35b0534e31', 'id3'),
                                       (-243.00, 3, TIMESTAMP WITH TIME ZONE '2024-04-15 14:12:23.109443+02', 5, UUID 'd9974137-7000-4f34-8c64-1d35b0534e31', 'id4');
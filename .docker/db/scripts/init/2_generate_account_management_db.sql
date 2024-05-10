SET SEARCH_PATH TO bank_user;

-- INSERT starting data

INSERT INTO bank_user.bank_user (id, email, password, first_name, last_name, user_type) VALUES (991, 'joe.smith@gmail.com', 'velosPodPeros', 'Joe', 'Smith', '0');
INSERT INTO bank_user.bank_user (id, email, password, first_name, last_name, user_type) VALUES (992, 'pedroPablo@gmail.com', 'velosPodPeros', 'Pedro', 'Pavelovic', '0');
INSERT INTO bank_user.bank_user (id, email, password, first_name, last_name, user_type) VALUES (993, 'sueZeman@gmail.com', 'einsZweiDreiPolizei', 'Zuzana', 'Zemanova', '1');
INSERT INTO bank_user.bank_user (id, email, password, first_name, last_name, user_type) VALUES (994, 'marcoMonilovic@gmail.com', 'iLovePizza123', 'Marko', 'Monilovic', '0');
INSERT INTO bank_user.bank_user (id, email, password, first_name, last_name, user_type) VALUES (995, 'jozefKuzma@gmail.com', 'manchesterUnitedSucks', 'Jozef', 'Kuzma', '1');
INSERT INTO bank_user.bank_user (id, email, password, first_name, last_name, user_type) VALUES (996, 'andrejSulek@gmail.com', 'milujemSpaceX', 'Andrej', 'Sulek', '1');
INSERT INTO bank_user.bank_user (id, email, password, first_name, last_name, user_type) VALUES (997, 'carradonnaS@gmail.com', 'pizzaPasta123', 'Sara', 'Carradonna', '1');
INSERT INTO bank_user.bank_user (id, email, password, first_name, last_name, user_type) VALUES (998, 'vercimP@gmail.com', 'teplaRybkaCSharp', 'Peter', 'Vercimak', '1');
INSERT INTO bank_user.bank_user (id, email, password, first_name, last_name, user_type) VALUES (999, 'ppetrovic@gmail.com', 'TatranskyCajCierny', 'Pavol', 'Petrovic', '1');
INSERT INTO bank_user.bank_user (id, email, password, first_name, last_name, user_type) VALUES (9910, 'obsivan@gmail.com', 'pletiemKorbace', 'Eva', 'Obsivanova', '0');
INSERT INTO bank_user.bank_user (id, email, password, first_name, last_name, user_type) VALUES (9911, 'vojtekj@gmail.com', 'kogmawIrl', 'Jakub', 'Vojtek', '0');
INSERT INTO bank_user.bank_user (id, email, password, first_name, last_name, user_type) VALUES (9912, 'sholak@gmail.com', 'pretekarisSpeedy', 'Stefan', 'Holakovsky', '0');
INSERT INTO bank_user.bank_user (id, email, password, first_name, last_name, user_type) VALUES (9913, 'danihelM@gmail.com', 'busimDoKlavecnice247', 'Marek', 'Danihel', '0');
INSERT INTO bank_user.bank_user (id, email, password, first_name, last_name, user_type) VALUES (9914, 'robko12@gmail.com', 'tigerMonster', 'Robert', 'Sulhanek', '0');
INSERT INTO bank_user.bank_user (id, email, password, first_name, last_name, user_type) VALUES (9915, 'rybnikar@gmail.com', 'swarchnicee', 'Stefan', 'Rybnikar', '0');
INSERT INTO bank_user.bank_user (id, email, password, first_name, last_name, user_type) VALUES (9916, 'karasMich@gmail.com', 'unixenjoyer', 'Michal', 'Karas', '0');
INSERT INTO bank_user.bank_user (id, email, password, first_name, last_name, user_type) VALUES (9917, 'michal.podstavek@gmail.com', 'bugJeFeature', 'Michal', 'Podstavek', '0');
INSERT INTO bank_user.bank_user (id, email, password, first_name, last_name, user_type) VALUES (9918, 'miro.rucka@gmail.com', 'jbossFuseJeKingOfPop', 'Miro', 'Rucka', '0');

INSERT INTO bank_user.bank_account (id, number, user_id, max_spending_limit, type, currency) VALUES (991, 'f8e26591-862c-4b02-9cdc-9a4505d3b31f', 1, 500, '0', 'USD');
INSERT INTO bank_user.bank_account (id, number, user_id, max_spending_limit, type, currency) VALUES (992, 'a29914e1-8017-4b1e-894a-d5379a0c9e88', 1, 5000, '1', 'USD');
INSERT INTO bank_user.bank_account (id, number, user_id, max_spending_limit, type, currency) VALUES (993, '4aa2d3ed-ed22-4e3f-986f-44cb7c182517', 2, 15000, '0', 'MXN');
INSERT INTO bank_user.bank_account (id, number, user_id, max_spending_limit, type, currency) VALUES (994, 'a462d7e5-3555-46fd-9810-d160b44afa44', 4, 1000, '0', 'CZK');
INSERT INTO bank_user.bank_account (id, number, user_id, max_spending_limit, type, currency) VALUES (995, '48196474-6958-4f1c-bc31-6c5ce9cf71e0', 10, 12000, '0', 'CZK');
INSERT INTO bank_user.bank_account (id, number, user_id, max_spending_limit, type, currency) VALUES (996, '2212f26f-5432-4b92-9124-6ac9cec56b60', 11, 12000, '0', 'CZK');
INSERT INTO bank_user.bank_account (id, number, user_id, max_spending_limit, type, currency) VALUES (997, '92fdb5db-586c-4fe8-a5bb-31c25881f3eb', 11, 600, '0', 'EUR');
INSERT INTO bank_user.bank_account (id, number, user_id, max_spending_limit, type, currency) VALUES (998, '1e8feba3-3783-46eb-88fb-aa359f88913d', 12, 4500, '0', 'EUR');
INSERT INTO bank_user.bank_account (id, number, user_id, max_spending_limit, type, currency) VALUES (999, 'b519f63b-d868-4713-a7c0-9d12c29f4198', 13, 500, '0', 'EUR');
INSERT INTO bank_user.bank_account (id, number, user_id, max_spending_limit, type, currency) VALUES (9910, 'dd2be61a-eaf6-4fad-ac79-f51f0e8319ae', 14, 500, '0', 'EUR');
INSERT INTO bank_user.bank_account (id, number, user_id, max_spending_limit, type, currency) VALUES (9911, 'd9e7ff31-03b3-40f7-a676-850bb30d37d5', 14, 100, '0', 'EUR');
INSERT INTO bank_user.bank_account (id, number, user_id, max_spending_limit, type, currency) VALUES (9912, '18693673-3b85-419c-9823-9fa86c96fea9', 15, 1400, '0', 'EUR');
INSERT INTO bank_user.bank_account (id, number, user_id, max_spending_limit, type, currency) VALUES (9913, '81f69736-1be7-4b8a-9aba-f2f8bdeec816', 16, 1559, '0', 'EUR');
INSERT INTO bank_user.bank_account (id, number, user_id, max_spending_limit, type, currency) VALUES (9914, 'c1e6dbc7-0304-427a-a9fb-e17f4dae2e9b', 17, 899, '0', 'EUR');
INSERT INTO bank_user.bank_account (id, number, user_id, max_spending_limit, type, currency) VALUES (9915, '0bd3000f-ff8d-4d42-b82a-c95d4ae5217d', 18, 9999, '0', 'EUR');

INSERT INTO bank_user.scheduled_payment (id, source_account_id, target_account_id, amount, currency_code, recurrence_type, recurrence_payment_day) VALUES (991, 991, 992, 150, 'USD', '1', 15);
INSERT INTO bank_user.scheduled_payment (id, source_account_id, target_account_id, amount, currency_code, recurrence_type, recurrence_payment_day) VALUES (992, 9913, 9914, 50, 'EUR', '1', 16);
INSERT INTO bank_user.scheduled_payment (id, source_account_id, target_account_id, amount, currency_code, recurrence_type, recurrence_payment_day) VALUES (993, 9913, 9914, 50, 'EUR', '1', 2);
INSERT INTO bank_user.scheduled_payment (id, source_account_id, target_account_id, amount, currency_code, recurrence_type, recurrence_payment_day) VALUES (994, 9914, 9915, 150, 'EUR', '1', 21);
INSERT INTO bank_user.scheduled_payment (id, source_account_id, target_account_id, amount, currency_code, recurrence_type, recurrence_payment_day) VALUES (995, 995, 996, 600, 'CZK', '0', 2);
INSERT INTO bank_user.scheduled_payment (id, source_account_id, target_account_id, amount, currency_code, recurrence_type, recurrence_payment_day) VALUES (996, 998, 9911, 95, 'EUR', '0', 2);
INSERT INTO bank_user.scheduled_payment (id, source_account_id, target_account_id, amount, currency_code, recurrence_type, recurrence_payment_day) VALUES (997, 993, 995, 595, 'MXN', '0', 2);
INSERT INTO bank_user.scheduled_payment (id, source_account_id, target_account_id, amount, currency_code, recurrence_type, recurrence_payment_day) VALUES (998, 998, 9912, 115, 'EUR', '0', 2);


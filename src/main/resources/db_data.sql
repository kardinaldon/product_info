INSERT INTO language (language_id) VALUES
('ru'),
('en');

INSERT INTO currency (currency_id) VALUES
('rub'),
('usd');

INSERT INTO product (product_id, title, description, language_id, price, currency_id, creation_date, modification_date) VALUES
(1, 'TestTitle1', 'TestDescription1', 'en', 100, 'usd', '2021-05-31 17:38:34.000000', '2021-05-31 17:38:34.000000'),
(2, 'TestTitle2', 'TestDescription2', 'en', 101, 'usd', '2021-05-31 17:38:34.000000', '2021-05-31 17:38:34.000000'),
(3, 'TestTitle3', 'TestDescription3', 'en', 102, 'usd', '2021-05-31 17:38:34.000000', '2021-05-31 17:38:34.000000'),
(4, 'ТестовоеНазвание4', 'ТестовоеОписание4', 'ru', 103, 'rub', '2021-05-31 17:38:34.000000', '2021-05-31 17:38:34.000000'),
(5, 'ТестовоеНазвание5', 'ТестовоеОписание5', 'ru', 104, 'rub', '2021-05-31 17:38:34.000000', '2021-05-31 17:38:34.000000'),
(6 ,'ТестовоеНазвание6', 'ТестовоеОписание6', 'ru', 105, 'rub', '2021-05-31 17:38:34.000000', '2021-05-31 17:38:34.000000');
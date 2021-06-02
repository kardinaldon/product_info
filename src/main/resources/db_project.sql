DROP DATABASE IF EXISTS product_info_db;
CREATE DATABASE product_info_db;

DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS language;
DROP TABLE IF EXISTS currency;

CREATE TABLE language (
                          language_id varchar not null,
                          PRIMARY KEY (language_id)
);

CREATE TABLE currency (
                          currency_id varchar not null,
                          PRIMARY KEY (currency_id)
);

CREATE TABLE product
(
    product_id serial not null,
    title varchar not null,
    description varchar,
    language_id varchar not null,
    price bigint not null,
    currency_id varchar not null,
    creation_date timestamp,
    modification_date timestamp,
    PRIMARY KEY (product_id),
    FOREIGN KEY (language_id) REFERENCES language(language_id) ON DELETE RESTRICT,
    FOREIGN KEY (currency_id) REFERENCES currency(currency_id) ON DELETE RESTRICT
);
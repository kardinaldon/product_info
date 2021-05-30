DROP DATABASE IF EXISTS product_info_db;
CREATE DATABASE product_info_db;

DROP TABLE IF EXISTS product;

CREATE TABLE product
(
    product_id serial not null,
    title varchar not null,
    description varchar,
    price bigint not null,
    creation_date timestamp not null,
    modification_date timestamp not null,
    PRIMARY KEY (product_id)
);
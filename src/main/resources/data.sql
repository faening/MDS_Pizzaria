DROP TABLE IF EXISTS TB_CUSTOMER CASCADE;
DROP TABLE IF EXISTS TB_PIZZA CASCADE;
DROP TABLE IF EXISTS TB_DRINK CASCADE;
DROP TABLE IF EXISTS TB_ORDER CASCADE;
DROP TABLE IF EXISTS TB_PIZZA_ORDER CASCADE;
DROP TABLE IF EXISTS TB_DRINK_ORDER CASCADE;

CREATE TABLE tb_customer (
  id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name varchar(255) NOT NULL,
  phone varchar(255) NOT NULL,
  address varchar(255) NOT NULL
);

CREATE TABLE tb_pizza (
  id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  description varchar(255) NOT NULL,
  price decimal(10, 2) NOT NULL,
  ingredients varchar(255) NOT NULL
);

CREATE TABLE tb_drink (
  id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  description varchar(255) NOT NULL,
  price decimal(10, 2) NOT NULL,
  packing_size varchar(255) NOT NULL
);

CREATE TABLE tb_order (
  id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  customer_id int NOT NULL,
  amount decimal(10, 2),
  delivery_type VARCHAR(20),
  order_date TIMESTAMP NOT NULL,
  FOREIGN KEY (customer_id) REFERENCES tb_customer(id)
);

CREATE TABLE tb_pizza_order (
    id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    pizza_id int NOT NULL,
    order_id int NOT NULL,
    FOREIGN KEY (pizza_id) REFERENCES tb_pizza(id),
    FOREIGN KEY (order_id) REFERENCES tb_order(id)
);

CREATE TABLE tb_drink_order (
    id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    drink_id int NOT NULL,
    order_id int NOT NULL,
    FOREIGN KEY (drink_id) REFERENCES tb_drink(id),
    FOREIGN KEY (order_id) REFERENCES tb_order(id)
);

INSERT INTO tb_customer (name, phone, address) VALUES ('Bob', '97 963-685', 'R Joe Phaex, 36');
INSERT INTO tb_customer (name, phone, address) VALUES ('Janny', '97 975-965', 'R Carther Doe, 97');
INSERT INTO tb_customer (name, phone, address) VALUES ('Carl', '97 669-584', 'R Magh Toeh, 85');

-- https://www.papajohns.co.nl/categories/pizza
INSERT INTO tb_pizza (description, price, ingredients) VALUES ('Hawaiian', 11.0, 'Pizzasaus, Mozzarella, Ham en Ananas');
INSERT INTO tb_pizza (description, price, ingredients) VALUES ('Spicy Chicken Ranch', 13.0, 'Ranch saus, kaas, gegrilde kip, champignon, verse tomaten, jalapenos, ranchsaus');
INSERT INTO tb_pizza (description, price, ingredients) VALUES ('Spicy Buffalo Chicken', 13.5, 'Ranch saus, kaas, bacon, uien, chicken poppers, jalapenos, buffalosaus');
INSERT INTO tb_pizza (description, price, ingredients) VALUES ('Papa`s Tuna Delight', 15.0, 'Pizzasaus, Mozzarella, Tonijn, Rode Uien, Suikermais, Zwarte Olijven');

INSERT INTO tb_drink (description, price, packing_size) VALUES ('Coca-Cola', 3.0, '350 ML');
INSERT INTO tb_drink (description, price, packing_size) VALUES ('Coca-Cola', 7.0, '1 L');
INSERT INTO tb_drink (description, price, packing_size) VALUES ('Guaraná', 2.0, '350 ML');
INSERT INTO tb_drink (description, price, packing_size) VALUES ('Fanta Laranja', 2.5, '350 ML');

INSERT INTO tb_order (customer_id, amount, delivery_type, order_date) VALUES (1, 18.00, 'DELIVERY', '2023-08-08 14:30:00');
INSERT INTO tb_pizza_order (pizza_id, order_id) VALUES (1, 1);
INSERT INTO tb_drink_order (drink_id, order_id) VALUES (2, 1);
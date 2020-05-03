DROP TABLE Bank IF EXISTS;
DROP TABLE Customer IF EXISTS;
DROP TABLE Account IF EXISTS;
DROP TABLE B_Transaction IF EXISTS;


CREATE TABLE Bank (
  bank_id         INTEGER IDENTITY PRIMARY KEY,
  name VARCHAR(30)
);

CREATE TABLE Customer (
  customer_id         INTEGER IDENTITY PRIMARY KEY,
  name VARCHAR(30),
  bank_id INTEGER
);

ALTER TABLE Customer ADD CONSTRAINT fk_customer_bank FOREIGN KEY (bank_id) REFERENCES Bank (bank_id);

CREATE TABLE Account (
  account_id         INTEGER IDENTITY PRIMARY KEY,
  account_name VARCHAR(30),
  current_balance FLOAT,
  overdraft FLOAT,
  customer_id INTEGER
);
ALTER TABLE Account ADD CONSTRAINT fk_account_customer FOREIGN KEY (customer_id) REFERENCES Customer (customer_id);



CREATE TABLE B_Transaction (
  transaction_id         INTEGER IDENTITY PRIMARY KEY,
  transaction_type VARCHAR(30),
  amount FLOAT,
  operation_date DATE,
  balance_after_transaction FLOAT,
  account_id INTEGER
);

ALTER TABLE B_Transaction ADD CONSTRAINT fk_transaction_account FOREIGN KEY (account_id) REFERENCES Account (account_id);

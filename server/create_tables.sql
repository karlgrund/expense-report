
DROP TABLE event;
DROP TABLE purchase;
DROP TABLE partial_payments;
DROP TABLE event_participants;
DROP TABLE users;

CREATE TABLE users(
  email varchar(255) PRIMARY KEY,
  first_name varchar(255) NOT NULL,
  last_name varchar(255) NOT NULL,
  hashed_password varchar(255) NOT NULL,
  is_admin boolean
);

CREATE TABLE purchase(
  uuid VARCHAR(255) PRIMARY KEY,
  event_name VARCHAR(225),
  price BIGINT NOT NULL,
  currency VARCHAR(255) NOT NULL,
  purchase_date timestamp NOT NULL
);

CREATE TABLE partial_payments(
  purchase_uuid VARCHAR(255),
  email VARCHAR(255) NOT NULL,
  amount BIGINT NOT NULL,
  PRIMARY KEY(purchase_uuid, email)
);

CREATE TABLE event_participants(
  event_name VARCHAR(255) NOT NULL,
  user_email VARCHAR(255) NOT NULL,
  PRIMARY KEY(event_name, user_email)
);

CREATE TABLE event(
  event_name VARCHAR(255) PRIMARY KEY
);

CREATE DATABASE db_tubespbokel7;

USE db_tubespbokel7;

CREATE TABLE users (
    username    VARCHAR(100) NOT NULL,
    password    VARCHAR(100) NOT NULL,
    name        VARCHAR(100) NOT NULL,
    token       VARCHAR(100),
    token_expired_at BIGINT,
    PRIMARY KEY (username),
    UNIQUE (token)
) ENGINE InnoDB;

drop table incomes;
CREATE TABLE incomes (
    id          VARCHAR(100) NOT NULL,
    username    VARCHAR(100) NOT NULL,
    balance     DECIMAL(10, 2) NOT NULL ,
    item_name    VARCHAR(100) NOT NULL,
    price       DECIMAL(10, 2) NOT NULL ,
    amount      INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY fk_users_incomes (username) REFERENCES users (username)

) ENGINE InnoDB;

drop table outcomes;
CREATE TABLE outcomes (
    id              VARCHAR(100) NOT NULL ,
    username        VARCHAR(100) NOT NULL,
    balance         DECIMAL(10, 2) NOT NULL ,
    outcome_name    VARCHAR(100) ,
    outcome_total   DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY fk_users_outcomes (username) REFERENCES users (username)
) ENGINE InnoDB;

DELETE FROM incomes;

DELETE FROM outcomes;

DELETE FROM users;


CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       first_name VARCHAR(255) NOT NULL,
                       last_name VARCHAR(255) NOT NULL,
                       birthdate VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       cpf VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       active BOOLEAN NOT NULL
);

INSERT INTO users (first_name, last_name, birthdate, email, cpf, password, active)
VALUES(
'User','Name', '1999/09/19', 'username@email.com', '999-999-999.99', '999999999', true
      )
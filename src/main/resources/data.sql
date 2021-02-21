DROP TABLE IF EXISTS users;

CREATE TABLE users (
  id VARCHAR(50) PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  lastname VARCHAR(50) NOT NULL,
  birthday DATE DEFAULT NULL,
  email VARCHAR(100) NOT NULL,
  gender VARCHAR(1) NOT NULL,
  nickname VARCHAR(100) NOT NULL,
  salt VARCHAR(100) NOT NULL,
  composedSecret VARCHAR(256) NOT NULL,
  lastLogin DATE DEFAULT NULL
);

INSERT INTO users VALUES (
 'ia224hfigf',
 'Nome Utente',
 'Cognome Utente',
 TO_DATE('22/12/1989', 'dd/mm/YYYY'),
 'email@email.it',
 'M',
 'password:puppa',
 'ciaoiosonoilsaledellavita',
 '7b21dc01d5b412552c31f5dc0d2bce96c6d39ecfa993ab6d430dfe87ab50dc4f',
 TO_DATE('21/02/2021', 'dd/mm/YYYY')
);
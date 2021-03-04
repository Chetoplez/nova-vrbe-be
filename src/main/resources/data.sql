DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS guild;

CREATE TABLE GenericUser (
  id VARCHAR(50) NOT NULL AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL,
  lastname VARCHAR(50) NOT NULL,
  birthday VARCHAR(20) DEFAULT NULL,
  email VARCHAR(100) NOT NULL,
  gender VARCHAR(2) NOT NULL,
  nickname VARCHAR(100) NOT NULL,
  salt VARCHAR(100) NOT NULL,
  composedSecret VARCHAR(256) NOT NULL,
  lastLogin VARCHAR(20) DEFAULT NULL,
  PRIMARY KEY (id)
);

INSERT INTO GenericUser VALUES (
 1,
 'Nome Utente',
 'Cognome Utente',
 '22/12/1989',
 'email@email.it',
 'M',
 'password:puppa',
 'ciaoiosonoilsaledellavita',
 '7b21dc01d5b412552c31f5dc0d2bce96c6d39ecfa993ab6d430dfe87ab50dc4f',
 '21/02/2021'
);

CREATE TABLE Guild (
  id VARCHAR(50) PRIMARY KEY,
  name VARCHAR(50) NOT NULL
);

INSERT INTO Guild VALUES (
 1,
 'Gilda'
);

COMMIT;
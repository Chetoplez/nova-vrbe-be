DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS guild;
DROP TABLE IF EXISTS characters;
DROP TABLE IF EXISTS CharactersHistory;
DROP TABLE IF EXISTS CharactersDescription;

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

CREATE TABLE Characters (
  characterId NUMBER PRIMARY KEY,
  characterName VARCHAR(50) NOT NULL,
  characterIcon VARCHAR(250) NOT NULL,
  gender VARCHAR(2) NOT NULL,
  status VARCHAR(15) NOT NULL,
  clevel NUMBER NOT NULL,
  experience NUMBER NOT NULL,
  totalExperience NUMBER NOT NULL,
  health NUMBER NOT NULL,
  healthStatus VARCHAR(20) NOT NULL,
  role VARCHAR(20) NOT NULL,
  PRIMARY KEY (characterId)
);

INSERT INTO Characters VALUES (
 1,
 'Marzio Paparzio',
 'url',
 'M',
 'PLEBEO',
 1,
 100,
 100,
 500,
 'SAZIO',
 'USER'
);

CREATE TABLE CharactersHistory(
    historyId NUMBER PRIMARY KEY,
    history VARCHAR(500)
);

INSERT INTO CharactersHistory VALUES(
    1,
    'Questa e la storia di come la mia vita e cambiata, capovolta, sottosopra e finita'
);

CREATE TABLE CharactersDescription(
    descriptionId NUMBER PRIMARY KEY,
    description VARCHAR(500)
);

INSERT INTO CharactersDescription VALUES(
    1,
    'Uno strafigo che riesce a sbucciare le mele utilizzando il mento'
);

CREATE TABLE CharactersStatistics(
    characterId NUMBER PRIMARY KEY NOT NULL,
    forza NUMBER NOT NULL,
    forzaModifier NUMBER NOT NULL,
    destrezza NUMBER NOT NULL,
    destrezzaModifier NUMBER NOT NULL,
    costituzione NUMBER NOT NULL,
    costituzioneModifier NUMBER NOT NULL,
    intelligenza NUMBER NOT NULL,
    intelligenzaModifier NUMBER NOT NULL,
    saggezza NUMBER NOT NULL,
    saggezzaModifier NUMBER NOT NULL
);

INSERT INTO CharactersStatistics VALUES(
    1,
    5,
    0,
    5,
    0,
    5,
    0,
    5,
    0,
    5,
    0
);

CREATE TABLE CharacterTemporaryEffect(
    id NUMBER NOT NULL AUTO_INCREMENT,
    characterId NUMBER NOT NULL,
    modifier NUMBER NOT NULL,
    stat VARCHAR(15) NOT NULL,
    PRIMARY KEY (id)
);

COMMIT;
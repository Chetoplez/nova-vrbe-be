DROP TABLE IF EXISTS GenericUser;
DROP TABLE IF EXISTS guild;
DROP TABLE IF EXISTS guildBank;
DROP TABLE IF EXISTS GUILDROLE;
DROP TABLE IF EXISTS GUILDMEMBER;
DROP TABLE IF EXISTS CHARACTER_CV;
DROP TABLE IF EXISTS Characters;
DROP TABLE IF EXISTS CharactersHistory;
DROP TABLE IF EXISTS CharactersDescription;
DROP TABLE IF EXISTS Chat;
DROP TABLE IF EXISTS PresentiLuogo;
DROP TABLE IF EXISTS ChatMessages;
DROP TABLE IF EXISTS Luoghi;
DROP TABLE IF EXISTS ChatMembers;
DROP TABLE IF EXISTS Inventory;
DROP TABLE IF EXISTS InventoryObject;
DROP TABLE IF EXISTS InventoryObjectEffect;
DROP TABLE IF EXISTS CharacterInventoryObject;

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
  GUILD_ID NUMBER PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  description varchar(300),
  guild_img varchar(250),
  ATTIVO BIT,
  statute TEXT,
  announcement VARCHAR(500),
  PRIMARY KEY (GUILD_ID)
);

INSERT INTO GUILD VALUES (
 1,
 'Legio XIII Gemina',
 'I Conquistatori della repubblica',
 '',
 1,
 'un sacco di testo qui dentro',
 'un Annuncio bello e accattivante qui dentro, una roba fighissima'
);

CREATE TABLE GUILDBANK (
GUILD_ID NUMBER PRIMARY KEY,
amount NUMBER,
PRIMARY KEY(GUILD_ID)
);

insert into GUILDBANK values (
   1,
   5000
);

CREATE TABLE GUILDROLE (
ROLE_ID NUMBER NOT NULL,
GUILD_ID NUMBER NOT NULL,
NAME VARCHAR(50) NOT NULL,
SALARY NUMBER NOT NULL,
MANAGER BIT NOT NULL,
ROLE_IMG VARCHAR(250),
DESCRIPTION VARCHAR(300),
GUILD_LEVEL NUMBER NOT NULL,
PRIMARY KEY (ROLE_ID,GUILD_ID)
);

INSERT INTO GUILDROLE VALUES
(1,1,'Legionario',30,0,'','Soldato di truppa preposto a formare le prime linee di combattimento',1)
,(2,1,'Optio',50,0,'','Soldato Esperto braccio destro del centurione, a lui vengono affidati compiti di gestione e addestramento',2)
,(3,1,'Centurio',90,0,'','Il capo di una centuria, commina punizioni e allena la truppa. La sua parola è legge per i suoi sottoposti',3)
,(4,1,'Tribunus',130,0,'','Ufficiale in comando al Legatus Legionis, organizza le centurie e dispone per la logistica ',4)
,(5,1,'Legatus',200,1,'','Il comandante in carica della legione, decide promozioni e allontanamenti, ha facolta di vita o morte dei suoi soldati',5);


CREATE TABLE GUILDMEMBER (
CHARACTER_ID NUMBER NOT NULL,
ROLE_ID NUMBER NOT NULL,
PRIMARY KEY(CHARACTER_ID,ROLE_ID)
);

insert into GUILDMEMBER VALUES (
1,5);

CREATE TABLE CHARACTER_CV (
CHARACTER_ID NUMBER NOT NULL,
ROLE_ID NUMBER NOT NULL,
ENROLLMENT_DATE DATE,
PRIMARY KEY (CHARACTER_ID,ROLE_ID,ENROLLMENT_DATE)
);

CREATE TABLE Characters (
  CHARACTER_ID NUMBER PRIMARY KEY,
  CHARACTER_NAME VARCHAR(50) NOT NULL,
  CHARACTER_ICON VARCHAR(250) NOT NULL,
  gender VARCHAR(10) NOT NULL,
  status VARCHAR(15) NOT NULL,
  clevel NUMBER NOT NULL,
  experience NUMBER NOT NULL,
  TOTAL_EXPERIENCE NUMBER NOT NULL,
  health NUMBER NOT NULL,
  HEALTH_STATUS VARCHAR(20) NOT NULL,
  role VARCHAR(20) NOT NULL,
  PRIMARY KEY (CHARACTER_ID)
);

INSERT INTO Characters VALUES (
 1,
 'Marzio Paparzio',
 'https://www.icon.com',
 'MASCHIO',
 'PLEBEO',
 1,
 100,
 100,
 500,
 'SAZIO',
 'USER'
);

CREATE TABLE CharactersHistory(
    CHISTORY_ID NUMBER PRIMARY KEY,
    history VARCHAR(500)
);

INSERT INTO CharactersHistory VALUES(
    1,
    'Questa e la storia di come la mia vita e cambiata, capovolta, sottosopra e finita'
);

CREATE TABLE CharactersDescription(
    DESCRIPTION_ID NUMBER PRIMARY KEY,
    description VARCHAR(500)
);

INSERT INTO CharactersDescription VALUES(
    1,
    'Uno strafigo che riesce a sbucciare le mele utilizzando il mento'
);

CREATE TABLE CharactersStatistics(
    CHARACTER_ID NUMBER PRIMARY KEY NOT NULL,
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
    CHARACTER_ID NUMBER NOT NULL,
    modifier NUMBER NOT NULL,
    stat VARCHAR(15) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE Chat(
    chatId NUMBER AUTO_INCREMENT NOT NULL,
    creationDate DATE NOT NULL,
    active BIT NOT NULL,
    idLuogo NUMBER NOT NULL,
    privateChat BIT NOT NULL,
    characterId NUMBER NOT NULL,
    expirationDate DATE NULL,
    fixed BIT NOT NULL,
    PRIMARY KEY (chatId)
);

CREATE TABLE ChatMembers(
    chatId NUMBER NOT NULL,
    characterId NUMBER NOT NULL
);

CREATE TABLE PresentiLuogo(
    idLuogo NUMBER NOT NULL,
    characterId NUMBER NOT NULL,
    PRIMARY KEY(idLuogo, characterId)
);

CREATE TABLE Luoghi(
    idLuogo NUMBER  NOT NULL AUTO_INCREMENT,
    descr VARCHAR(MAX),
    nomeLuogo VARCHAR(100),
    statoLuogo VARCHAR(100),
    immagine VARCHAR(200),
    PRIMARY KEY(idLuogo)
);

insert into Luoghi values
(1,'Il forum è la piazza principale della cittadina dove si svolgono le maggiori attività economiche e sociali, si trovano gli edifici di governo e quelli finanziari. Si tratta di una piazza porticata con colonnato su tre lati circodata da vari edifici',
'Forum','','');

insert into Luoghi values (
2,'Porta di ingresso posta sul Decumano del Municipium. Vi sono due torrette di avvisatamento e sulle mura è posto un camminamenento che corre attorno alla fortificazione. Accanto al portone principali, vi sono due altre porte utilizzate per il passaggio di civili',
'Porta Decumana', '','');

CREATE TABLE ChatMessages (
    ID NUMBER NOT NULL AUTO_INCREMENT,
    CHATID NUMBER  NOT NULL,
    cACTION VARCHAR(12) NOT NULL,
    CARICA VARCHAR(50),
    IMG VARCHAR(500),
    RECEIVER VARCHAR(100),
    SENDER VARCHAR(100) NOT NULL,
    TAG VARCHAR (50),
    TESTO TEXT,
    CTIMESTAMP DOUBLE NOT NULL,
    TOOLTIP_CARICA VARCHAR NOT NULL
);

CREATE TABLE Inventory(
    CHARACTER_ID NUMBER NOT NULL,
    gold NUMBER,
    PRIMARY KEY(CHARACTER_ID)
);

insert into Inventory values(1,1895);

CREATE TABLE InventoryObject(
    ITEM_ID NUMBER NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(50) NOT NULL,
    isEquipment BIT NOT NULL,
    isRare BIT NOT NULL,
    category VARCHAR(15) NOT NULL,
    bodyPart VARCHAR(15),
    duration NUMBER,
    url VARCHAR(500),
    PRIMARY KEY(ITEM_ID)
);

INSERT INTO InventoryObject(ITEM_ID,name,description,isEquipment,isRare,category,bodyPart,duration,url) VALUES(
1,'elmo centurio','elmo con cresta trasversale',1,0,'ARMOR','HEAD',8,'https://www.coltelleriazoppi.com/wp-content/uploads/2010/11/p-35893-I6053.09.jpg'
);

CREATE TABLE InventoryObjectEffect(
    id NUMBER NOT NULL,
    inventoryObjectId NUMBER NOT NULL,
    healthStatus VARCHAR(15),
    healing NUMBER,
    stat VARCHAR(15),
    isTemporary BIT,
    duration NUMBER,
    isOneShot BIT,
    modifier NUMBER
);

INSERT INTO InventoryObjectEffect VALUES(1,1,'NONE',3,'COSTITUZIONE',0,99999,0,4 );

CREATE TABLE CharacterInventoryObject(
    idInventoryObject NUMBER NOT NULL,
    characterId NUMBER NOT NULL,
    quantity NUMBER,
    inUse BIT,
    duration NUMBER,
    acquiringDate DATE,
    acquiredBy NUMBER,
    PRIMARY KEY(idInventoryObject, characterId)
);

INSERT INTO CharacterInventoryObject VALUES (1,1,1,0,88,'2021-03-18',1);

COMMIT;
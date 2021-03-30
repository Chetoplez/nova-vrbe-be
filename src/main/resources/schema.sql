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
CREATE TABLE CharactersHistory(
    CHISTORY_ID NUMBER PRIMARY KEY,
    history VARCHAR(500)
);
CREATE TABLE CharactersDescription(
    DESCRIPTION_ID NUMBER PRIMARY KEY,
    description VARCHAR(500)
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

CREATE TABLE CharacterTemporaryEffect(
    id NUMBER NOT NULL AUTO_INCREMENT,
    CHARACTER_ID NUMBER NOT NULL,
    modifier NUMBER NOT NULL,
    stat VARCHAR(15) NOT NULL,
    PRIMARY KEY (id)
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
CREATE TABLE GUILDBANK (
GUILD_ID NUMBER PRIMARY KEY,
amount NUMBER,
PRIMARY KEY(GUILD_ID)
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

CREATE TABLE GUILDMEMBER (
CHARACTER_ID NUMBER NOT NULL,
ROLE_ID NUMBER NOT NULL,
PRIMARY KEY(CHARACTER_ID,ROLE_ID)
);
CREATE VIEW V_GUILDMEMBERS
AS
SELECT
 GR.GUILD_ID AS GUILD_ID,
 GR.ROLE_ID AS ROLE_ID,
 CH.CHARACTER_ID AS CHARACTER_ID,
 CH.CHARACTER_NAME AS CHARACTER_NAME,
 GR.GUILD_LEVEL AS GUILD_LEVEL,
 GR.NAME AS GUILD_NAME,
 GR.ROLE_IMG AS ROLE_IMG
FROM GUILDMEMBER GM
JOIN GUILDROLE GR
ON GM.ROLE_ID = GR.ROLE_ID
JOIN CHARACTERS AS CH
ON CH.CHARACTER_ID = GM.CHARACTER_ID;

CREATE TABLE CHARACTER_CV (
CHARACTER_ID NUMBER NOT NULL,
ROLE_ID NUMBER NOT NULL,
ENROLLMENT_DATE DATE,
PRIMARY KEY (CHARACTER_ID,ROLE_ID,ENROLLMENT_DATE)
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


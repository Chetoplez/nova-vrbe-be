
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
),
(
 2,
 'Ciccio ',
 'Putin',
 '22/12/1989',
 'email@email.it',
 'M',
 'password:puppa',
 'ciaoiosonoilsaledellavita',
 '7b21dc01d5b412552c31f5dc0d2bce96c6d39ecfa993ab6d430dfe87ab50dc4f',
 '21/02/2021'
),
(
 3,
 'Ajeje ',
 'Brazorf',
 '22/12/1989',
 'email@email.it',
 'M',
 'password:puppa',
 'ciaoiosonoilsaledellavita',
 '7b21dc01d5b412552c31f5dc0d2bce96c6d39ecfa993ab6d430dfe87ab50dc4f',
 '21/02/2021'
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
 100,
 'SAZIO',
 'USER'
),(
   2,
   'Sergio Claudio',
   'https://www.icon.com',
   'MASCHIO',
   'PLEBEO',
   1,
   100,
   100,
   100,
   'SAZIO',
   'USER'
  ),(
       3,
       'Gaio Cesare',
       'https://www.icon.com',
       'MASCHIO',
       'PLEBEO',
       1,
       100,
       100,
       100,
       'SAZIO',
       'USER'
      );


INSERT INTO CharactersHistory VALUES
(1,'Questa e la storia di come la mia vita e cambiata, capovolta, sottosopra e finita'),
(2,'Questa e la storia di uno di noi, anche lui nato per caso in via Gluk'),
(3,'Nel mezzo del cammin di nostra vita mi trovai per una selrva oscura e dissi: minchia mi pessi');


INSERT INTO CharactersDescription VALUES
(1,'Uno strafigo che riesce a sbucciare le mele utilizzando il mento'),
(2,'alto bello un fotomodello'),
(3,'basso , tarchiato ma con un cuore cosi');



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
),(
      2,
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
  ),(
        3,
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



INSERT INTO GUILD VALUES (
 1,
 'Legio XIII Gemina',
 'I Conquistatori della repubblica',
 '',
 1,
 'un sacco di testo qui dentro',
 'un Annuncio bello e accattivante qui dentro, una roba fighissima'
),
(
 2,
 'Medici',
 'Studiosi delle arti curatorie, fedeli al giuramento di Ippocrate',
 '',
 1,
 'lo statuto dei medici',
 'un Annuncio bello e accattivante qui dentro, una roba fighissima , ma dei medici'
);



insert into GUILDBANK values (
   1,
   5000
),(2,5000);



INSERT INTO GUILDROLE VALUES
(1,1,'Legionario',30,0,'','Soldato di truppa preposto a formare le prime linee di combattimento',10)
,(2,1,'Optio',50,0,'','Soldato Esperto braccio destro del centurione, a lui vengono affidati compiti di gestione e addestramento',20)
,(3,1,'Centurio',90,0,'','Il capo di una centuria, commina punizioni e allena la truppa. La sua parola è legge per i suoi sottoposti',30)
,(4,1,'Tribunus',130,0,'','Ufficiale in comando al Legatus Legionis, organizza le centurie e dispone per la logistica ',40)
,(5,1,'Legatus',200,1,'','Il comandante in carica della legione, decide promozioni e allontanamenti, ha facolta di vita o morte dei suoi soldati',50)
,(6,2,'Discipulo',30,0,'','Colui che si è appena iniziato allo studio delle arti mediche, non ha ancora effettuato il giuramento ad Ippocrate',10)
,(7,2,'Balneator',90,0,'','Il gestore della parte termale, colui che conosce il corpo, le arti dei massaggi e degli unguenti',20)
,(8,2,'Medicus',90,0,'','Scieziato dell arte medica, riesce a a curare malanni, ferite e sa come alleviare dolori',21)
,(9,2,'Medicus Specialistis',130,0,'','Un medico specializzato in un settore che conosce a fondo la sua materia e le varie tecniche di cura, oltre alla medicina generale',40)
,(10,2,'Primarius',200,0,'','Il gestore della valetudinaria del municipium, da compiti, lezioni, promozioni e sanzioni. Un medico esperto sia nell arte medica che quella governativa',50);



insert into GUILDMEMBER VALUES
(1,1);



insert into Luoghi values
(1,'Il forum è la piazza principale della cittadina dove si svolgono le maggiori attività economiche e sociali, si trovano gli edifici di governo e quelli finanziari. Si tratta di una piazza porticata con colonnato su tre lati circodata da vari edifici',
'Forum','',''),
(2,'Porta di ingresso posta sul Decumano del Municipium. Vi sono due torrette di avvisatamento e sulle mura è posto un camminamenento che corre attorno alla fortificazione. Accanto al portone principali, vi sono due altre porte utilizzate per il passaggio di civili',
'Porta Decumana', '','');



insert into Inventory(character_id,gold) values(1,1895);

INSERT INTO InventoryObject(ITEM_ID,name,description,isEquipment,isRare,category,bodyPart,duration,url) VALUES(
1,'elmo centurio','elmo con cresta trasversale',1,0,'ARMOR','HEAD',8,'https://www.coltelleriazoppi.com/wp-content/uploads/2010/11/p-35893-I6053.09.jpg'
);



INSERT INTO InventoryObjectEffect(effect_ID,INVENTORYOBJECTID,HEALTHSTATUS,HEALING,STAT,ISTEMPORARY,DURATION,ISONESHOT,MODIFIER) VALUES(1,1,'NONE',3,'COSTITUZIONE',0,99999,0,4 );


INSERT INTO CharacterInventoryObject(idInventoryObject,characterId,quantity,inUse,duration,acquiringDate,acquiredBy) VALUES (1,1,1,0,88,'2021-03-18',1);

COMMIT;
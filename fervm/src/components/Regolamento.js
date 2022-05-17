import { maxHeight } from '@mui/system';
import React from 'react'
import HomeNav from './Navbar/HomeNav';
function Regolamento() {
    
    document.title = 'Fervm GdR - Regolamento'
    var body = document.getElementById("body")
    body.classList.remove("home-img")
    
    return(
        <>
        <HomeNav />
        <div className="under-nav">
            <h1>Regolamento</h1>
            <section className='contenitori' style={{whiteSpace:'pre-wrap', overflow:'auto', maxHeight:'80vh'}}>
            <h3>Il presente regolamento è ancora una bozza, verrà ampliato e migliorato in fase di rilascio definitivo</h3>
                <p>
                Iscrivendosi si accetta implicitamente il regolamento in ogni suo punto e ci si impegna a rispettarlo.
                Eventuali modifiche del regolamento saranno tempestivamente comunicate alla comunità che dovrà prenderne visione
                </p>
                <p>
                Fervm è un GDR Play by Chat ambientato nel intorno al 50- 57 a.C.. Si tratta di un gioco a sfondo storico per cui, pur volendo tenere conto quanto più possibile della veridicità di situazioni ed ambientazioni, è stato necessario utilizzare delle licenze per rendere il gioco fruibile per tutti.
                </p>
                <p>
                Per l’iscrizione è richiesta la maggiore età. Chiunque abbia un’età inferiore a quella indicata ed entri comunque all’interno del gioco si assume personalmente ogni responsabilità esonerando la gestione di ogni possibile problematica. L’età minima del pg invece è di 16 anni
                </p>
                <p>
                Il giocatore si deve attenere al periodo storico, giocando quanto più possibile con coerenza. La violazione di questa norma di base comporterà un iniziale richiamo e, in caso di reiterata mancanza di osservazione del contesto, al ban
                </p>
                <p>
                Al momento è possibile avere solo un account ma è in sviluppo un sistema che consenta la creazione di due pg con lo stesso account da poter utilizzare in modo esclusivo. Chiunque condivida con un parente lo stesso terminale è pregato di comunicarlo alla Gestione indicando il nome dei PG coinvolti. 
                </p>
                <p>
                Il nome del personaggio, così come il background, devono essere consoni al periodo storico. Sarà possibile utilizzare nomi stranieri solo se comprovati da un bg appropriato e purchè siano sempre coerenti con il periodo di gioco. In ogni caso non è concesso utilizzare nomi poco adeguati o offensivi, così come nomi di personaggi storici realmente esistiti (ad esempio Giulio Cesare). Se, su segnalazione dello staff, si richiede il cambio nome e non vi è un tempestivo cambiamento la pena è il ban immediato. È possibile cambiare nome al proprio pg una volta e solo se non è mai stata svolta alcuna giocata. A tale scopo è necessario contattare il gestore o un admin .
                Per ulteriori informazioni riguardo la nomenclatura romana si rimanda alla apposita sezione all’interno dei forum
                </p>
                <p>
                Il linguaggio richiesto, sia in chat che nelle schede dei personaggi, così come nei forum, è l’italiano corrente e corretto. In chat è possibile utilizzare, nel parlato, cenni di altre lingue, se il bg lo giustifica, ma senza esagerare.
                È vietato utilizzare nelle azioni forme dialettali che potrebbero rendere difficile l’interazione.
                È vietato altresì l’utilizzo di parole eccessivamente volgari e bestemmie oltre che ingiurie e insulti di qualsiasi natura che possono essere lesive della suscettibilità personale. 
                In caso di trasgressione a questa regola segue un iniziale richiamo e, in caso di recidiva, il ban temporaneo.
                </p>
                <p>È vietato avere atteggiamenti da PowerPlayer (PP) nonché incorrere in atti di Metagame in quanto contrati alla base del Gioco di Ruolo e dell’interazione.
                In caso di segnalazione di tali comportamenti si procederà da un richiamo al ban definitivo del personaggio
                </p>
                <p>
                L’Antigioco verrà punito in base alla gravità e alla reiterazione del comportamento adottato.
                È considerato antigioco sparire da un luogo, volare, ascoltare un sussurro o compiere qualsiasi azione impossibile per le capacità umane, entrare ed attaccare senza lanciare i dadi o palesarsi, fuggire senza lanciare il dado, far apparire un’arma a metà giocata senza averla indicata nella prima azione, avere un’arma e farla sparire in caso di arresto, parlare o sussurrare senza giungere in un luogo, ogni azione contraria alla storicità e alla realisticità del gioco e ogni  altro caso da valutare volta per volta dallo Staff previo invio della giocata.
                </p>
                <p>
                    Non sono consentite in alcuna fascia oraria giocate relative a violenze sessuali, maltrattamenti di minori o animali sia in chat pubbliche che private.
                    La gestione si riserva un controllo periodico per verificare che non vi siano infrazioni e, in caso contrario, la pena è il ban immediato. È possibile giocare battaglie, agguati, rapimenti e scontri tra personaggi ma senza descrivere in maniera troppo cruenta o splatter spargimenti di sangue o ferite in orario diurno.
                    Sarà punito con il ban qualsiasi incitamento OFF Game a praticare atti immorali, violenti o illegali.
                    È vietato avere un comportamento discriminatorio di tipo razziale, religioso, sociale o sessuale nei confronti dei player in qualsiasi ambiente della land compreso sussurri e missive.
                    Riferimenti eccessivamente espliciti ad atti sessuali sono vietati in chat pubbliche, via missive o sussurri.
                    Nelle chat private è possibile, previo consenso delle parti, trattare tali argomenti in maniera moderata e purchè non diventi il solo fine di gioco.
                    Si ricorda che Fervm non è una land erotica, per cui lo staff si riserva di richiamare i player che prediligeranno il solo gioco in privato.
                </p>
            </section>
        </div>
        </>
    )
} export default Regolamento;
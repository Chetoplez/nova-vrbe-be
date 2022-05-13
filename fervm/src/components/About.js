import React from 'react'
import HomeNav from './Navbar/HomeNav';

function About() {
    var body = document.getElementById("body")
    body.classList.remove("home-img")
    document.title = 'Fervm GdR - About'
    return(
        <>
        <HomeNav />
        <div className="under-nav">
        <div style={{textAlign:'justify'}}>
            <p>Benvenuto! mi chiamo Claudio e sono un appassionato di storia dell'antica Roma , programmazione web, e giochi di Ruolo Play By Chat. 
                Dall'unione di queste tre passioni è nato questo progetto che è ancora in fase di sviluppo e consiste in un gioco di ruolo con ambientazione antica Roma  
                totalmente sviluppato da me, con l'aiuto di alcuni amici. </p>
                <p>L'idea è nata per far fronte ad alcuni bisogni personali, avevo bisogno di uno stimolo per imparare una nuova tecnologia con cui arricchire il mio bagaglio di conoscenze , 
                riuscire a pubblicare un progetto per una platea online e infine, quello di modernizzare il mondo del GDR Play By Chat con una tecnologia moderna
                accattivante e che si staccasse del tutto dallo scenario attuale del gdr PbC. </p>
                <p>Con queste premesse, inseme ad un gruppo di amici , stiamo dando vita a FERVM, una nuova land dove gli appassionati di storia romana, di scrittura e di gioco di ruolo 
                possono incontrarsi per divertirsi e giocare insieme!</p>

            </div>
        </div>
        </>
    )
} export default About;
import React , { useContext, useState } from 'react'
import {userContext} from '../../utils/userContext'

import MissiveInbox from './MissiveInbox';
import DettaglioMissiva from './DettaglioMissiva';
import './MissivePage.css'

function MissivaPage() {
    const mainContext = useContext(userContext);
    const [missiva, setMissiva] = useState(null);

    return(
        <div className='w3-row'>
            <header>
                <h2>Epistolario di {mainContext.user.characterName + ' ' + mainContext.user.characterSurname}</h2>
            </header>
            <section style={{marginRight:'5px'}} className='w3-quarter colonna-dx'>
                <MissiveInbox setMissiva={setMissiva} chId={mainContext.user.characterId} />
            </section>
            <section  className='w3-threequarter colonna-sx'>
               {missiva && (<DettaglioMissiva setMissiva={setMissiva} missiva={missiva} />)}
            </section>
        </div>
    )

} export default MissivaPage;
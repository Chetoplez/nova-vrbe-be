import React , { useContext, useState } from 'react'
import {userContext} from '../../utils/userContext'

import MissiveInbox from './MissiveInbox';
import WriteMissiva from './WriteMissiva';
import DettaglioMissiva from './DettaglioMissiva';
import './MissivePage.css'

function MissivaPage() {
    const mainContext = useContext(userContext);
    const [missiva, setMissiva] = useState(null);
    const [newMissiva , setNewMissiva] = useState(false);

    return(
        <div className='w3-row'>
            <header>
                <h3>Epistolario di {mainContext.user.characterName + ' ' + mainContext.user.characterSurname}</h3>
            </header>
            <section style={{marginRight:'5px'}} className='w3-quarter colonna-dx'>
                <MissiveInbox setNewMissiva={setNewMissiva} setMissiva={setMissiva} chId={mainContext.user.characterId} />
            </section>
            <section  className='w3-threequarter colonna-sx'>
                {newMissiva ? 
                    <WriteMissiva setNewMissiva={setNewMissiva}/> :
                    (missiva && <DettaglioMissiva setMissiva={setMissiva} missiva={missiva} />) }
            </section>
        </div>
    )

} export default MissivaPage;
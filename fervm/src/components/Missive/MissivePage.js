import React , { useContext, useState } from 'react'
import {userContext} from '../../utils/userContext'

import MissiveList from './MissiveList';
import WriteMissiva from './WriteMissiva';
import DettaglioMissiva from './DettaglioMissiva';
import './MissivePage.css'

function MissivaPage() {
    const mainContext = useContext(userContext);
    const [missiva, setMissiva] = useState(null);
    const [section , setSection] = useState('');

    const returnSection = ()=>{
        var sezione = null
        switch(section){
            case 'leggi':
                sezione = <DettaglioMissiva setSection={setSection} setMissiva={setMissiva} missiva={missiva} />
                break;
            case 'scrivi':
                sezione = <WriteMissiva setSection={setSection}/>
                break;
            case 'rispondi':
                sezione = <WriteMissiva missiva={missiva} setSection={setSection}/>
                break;
            case '':
                sezione = null;
                break;
            default:
                sezione = null;
        }

        return sezione;
    }

    return(
        <div className='w3-row'>
            <header>
                <h3>Epistolario di {mainContext.user.characterName + ' ' + mainContext.user.characterSurname}</h3>
            </header>
            <section style={{marginRight:'5px'}} className='w3-quarter colonna-dx'>
                <MissiveList setSection={setSection} setMissiva={setMissiva} chId={mainContext.user.characterId} />
            </section>
            <section  className='w3-threequarter colonna-sx'>
                { returnSection() }
            </section>
        </div>
    )
    
} export default MissivaPage;
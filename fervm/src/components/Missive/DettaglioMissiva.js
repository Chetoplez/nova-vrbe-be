import React from "react";
import moment from "moment";
import SmallCharacter from '../../utils/SmallCharacter'
import './DettaglioMissiva.css';
import { sanitazeHTML } from "../../utils/api";
import parse from 'html-react-parser';

function DettaglioMissiva ({ missiva, setMissiva }){

    return(
        <div className="contenitori">
            <button className='ctrl-btn-M w3-right' onClick={()=>{setMissiva(null)}}>X</button>
            <SmallCharacter character={missiva.from}/>
            <div>
                <div><strong>Oggetto:</strong> {missiva.subject}</div>
                <div><strong>Ricevuta:</strong> {moment(missiva.receivedAt).format("DD/MM/YYYY HH:mm")} </div>
                
            </div>
            <hr style={{border: '1px solid black'}}></hr>
            <section className="missivaBody">
                {parse(sanitazeHTML(missiva.body))}
            </section>
        </div>
    )
} export default DettaglioMissiva;
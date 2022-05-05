import React from "react";
import moment from "moment";
import SmallCharacter from '../../utils/SmallCharacter'
import './DettaglioMissiva.css';
import { sanitazeHTML } from "../../utils/api";
import IconButton from '@material-ui/core/IconButton';
import CloseIcon from '@mui/icons-material/Close';
import ReplyIcon from '@mui/icons-material/Reply';
import parse from 'html-react-parser';

function DettaglioMissiva ({ missiva, setMissiva }){

    return(
        <div className="contenitori">
            <section className="w3-row">
                <div className="w3-half">
                    Da: <SmallCharacter character={missiva.from}/>
                    <div>
                        <div><strong>Oggetto:</strong> {missiva.subject}</div>
                        <div><strong>Ricevuta:</strong> {moment(missiva.receivedAt).format("DD/MM/YYYY HH:mm")} </div>
                    </div>
                </div>
                <div className="w3-half">
                    <IconButton className='w3-right' onClick={()=>{setMissiva(null)}}>
                        <CloseIcon />
                    </IconButton>
                    <IconButton className='w3-right' onClick={()=>{setMissiva(null)}}>
                        <ReplyIcon />
                    </IconButton>
                </div>
            </section>
            <hr style={{border: '1px solid black'}}></hr>
            <section className="missivaBody">
                {parse(sanitazeHTML(missiva.body))}
            </section>
        </div>
    )
} export default DettaglioMissiva;
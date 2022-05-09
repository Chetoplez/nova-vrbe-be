import React from "react";
import moment from "moment";
import SmallCharacter from '../../utils/SmallCharacter'
import './DettaglioMissiva.css';
import { sanitazeHTML } from "../../utils/api";
import IconButton from '@material-ui/core/IconButton';
import CloseIcon from '@mui/icons-material/Close';
import ReplyIcon from '@mui/icons-material/Reply';
import parse from 'html-react-parser';
import DeleteIcon from '@material-ui/icons/Delete';
import { API_URL, getJwt } from "../../utils/api";
import axios from "axios";

function DettaglioMissiva({ missiva, setMissiva, setSection }) {

    const deleteMissiva = ()=>{
        var payload = {
            idMissive: [missiva.missivaId]
        }
        axios.delete(API_URL.MISSIVE + '/delete',  {data: payload})
                .then(resp=>{        
                    if (resp.data.succes){
                        setSection('')
                    }
                })
    }
    return (
        <div className="contenitori">
            <section className="w3-row">
                <div className="w3-half">
                    Da: <SmallCharacter character={missiva.from} />
                    <div>
                        <div><strong>Oggetto:</strong> {missiva.subject}</div>
                        <div><strong>Ricevuta:</strong> {moment(missiva.receivedAt).format("DD/MM/YYYY HH:mm")} </div>
                    </div>
                </div>
                <div className="w3-half">
                    <IconButton className='w3-right' type="button" onClick={() => { setMissiva(null); setSection('') }}>
                        <CloseIcon />
                    </IconButton>
                    <IconButton className='w3-right' type="button" onClick={() => { setSection('rispondi'); setMissiva(missiva) }}>
                        <ReplyIcon />
                    </IconButton>
                    <IconButton className='w3-right' name="delete" aria-label="delete" type="button" onClick={deleteMissiva}>
                        <DeleteIcon />
                    </IconButton>
                </div>
            </section>
            <hr style={{ border: '1px solid black' }}></hr>
            <section className="missivaBody">
                {parse(sanitazeHTML(missiva.body))}
            </section>
        </div>
    )
} export default DettaglioMissiva;
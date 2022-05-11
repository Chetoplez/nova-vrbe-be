import React, { useEffect, useState, useContext } from 'react'
import { userContext } from '../../utils/userContext';

import Select from 'react-select';
import makeAnimated from 'react-select/animated';
import useMessage from '../../utils/useMessage';

import IconButton from '@material-ui/core/IconButton';
import CloseIcon from '@mui/icons-material/Close';
import axios from 'axios';
import { API_URL, getJwt, sanitazeHTML } from '../../utils/api';
import { TextField } from '@material-ui/core';


function WriteMissiva({ setSection , missiva } ) {
    const mainContext = useContext(userContext);
    const animatedComponents = makeAnimated();
    const [characterOption, setCharacterOption] = useState([{ value: "", label: "" }])
    const [subject, setSubject] = useState("");
    const [body, setBody] = useState('');
    const [type, setType] = useState({value: "ON", label: "ON"})
    const [to, setTo] = useState({value: "", label: ""})
    const { addMessage } = useMessage();
    
    useEffect(() => {
        
       if(missiva !== undefined ){
           setType({ label: missiva.type, value: missiva.type})
           setTo({value: missiva.from.characterId , label: missiva.from.characterName +' '+missiva.from.characterSurname})
           setSubject('RE: '+missiva.subject)
           setBody("\n\n--------\n["+missiva.body+']')
       } 
        
        axios.get(API_URL.CHARACTER + '/getcharacterlist',
            {
                headers: {
                    'Authorization': 'Fervm ' + getJwt()
                }
        }).
        then(resp => {
                var temp = []
                resp.data.characterList.map(smallch => {
                    var chOpt = {
                        value: smallch.characterId,
                        label: smallch.characterName + ' ' + smallch.characterSurname
                    }
                    temp.push(chOpt)
                })

                setCharacterOption(temp);
            })
    }, [])


    const handleSubject = (evt) => {
        setSubject(evt.target.value);
    }
    
    const handleChange = (obj)=>{
        var temp = characterOption.find(elem=> elem.value == obj.value)
        setTo(temp);
        console.log("Destinatario",to)
    }
    const handleTypeChange = (obj)=>{
        console.log(obj)
        setType(obj)
    }
    const handleTextChange = (obj)=>{
        setBody(obj.target.value)
    }

    const send = ()=>{
        var payload = {
            missiva: {
                to: {
                    characterId: to.value
                },
                from: mainContext.user,
                body : sanitazeHTML(body),
                subject: sanitazeHTML(subject),
                sentAt: Date.now(),
                receivedAt: Date.now(),
                isRead: false,
                type: sanitazeHTML(type.value)
            }
        }
        axios.post(API_URL.MISSIVE +'/send' , payload, {
            headers: {
              'Authorization': 'Fervm '+getJwt()
            }})
        .then(resp=>{
            addMessage(resp.data.message);
            setSection('')
        }).catch(err=>{
            console.log(err)
        })
    }

    return (

        <div className='contenitori'>
            <section className="w3-row">
                <div className="w3-half">
                    <div style={{ alignItems: 'center', marginTop: '10px' }}>
                        <strong>Destinatario:</strong>
                        <Select
                            onChange={handleChange}
                            closeMenuOnSelect={true}
                            components={animatedComponents}
                            value={to}
                            options={characterOption}
                        />
                    </div>

                    <div>
                        <div className='d-flex' style={{ alignItems: 'center', marginTop: '10px', justifyContent: 'space-evenly' }}>
                            <strong>Oggetto:</strong>
                            <TextField style={{ marginLeft: '20px' }} type="text" name="subject" onChange={handleSubject} value={subject}></TextField>
                            <strong>Tipo:</strong>
                            <Select
                            onChange={handleTypeChange}
                            closeMenuOnSelect={true}
                            components={animatedComponents}
                            value={type}
                            options={[{label: "ON", value: "ON"}, {label: 'OFF', value: 'OFF'}]}
                        />
                        </div>
                    </div>
                </div>
                <div className="w3-half">
                    <IconButton className='w3-right' onClick={() => { setSection('') }}>
                        <CloseIcon />
                    </IconButton>

                </div>
            </section>
            <hr style={{ border: '1px solid black' }}></hr>
            <section>
            <textarea 
                    type='textarea'
                    aria-multiline
                    name='body' 
                    className='postTextArea'
                    onChange={handleTextChange}
                    value={sanitazeHTML(body)}
                    >
                    
             </textarea>
             <button className='primary-btn-M' type='button' onClick={send}>Invia</button>
            </section>
        </div>

    )
} export default WriteMissiva;
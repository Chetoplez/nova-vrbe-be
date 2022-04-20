import { Button, TextField, Radio, RadioGroup, FormControlLabel, Checkbox, FormControl, Input, InputLabel, Select, MenuItem } from '@material-ui/core';
import ToggleButton from '@material-ui/lab/ToggleButton';
import ToggleButtonGroup from '@material-ui/lab/ToggleButtonGroup';
import React, { useState, useContext } from 'react';
import axios from 'axios';
import { userContext } from '../../utils/userContext'
import '../Chat/ControlliChat.css'
import { API_URL } from '../../utils/api';
import store from 'store';
function ControlliChat(props) {
    const mainContext = useContext(userContext);

    const [text, setText] = useState("");
    const [sendOnEnter, setSendOnEnter] = useState(false)
    const [value, setValue] = useState('D');
    const [tag, setTag] = useState("");
    const [placeTag, setPlaceTag] = useState("Tag")
    const [button, setButton] = useState("Parla")
    const [dice, setDice] = useState(false);
    const [nomeSussurro, setNomeSussurro] = useState("")
    const [presentiChat, setPresentiChat] = useState([])
    

    const HandleTextChange = (evt) => {
        setText(evt.target.value);
    }

    const HandleTagChange = (evt) => {
        setTag(evt.target.value);
    }

    const submitMessage = (evt) => {
        if (evt.keyCode === 13 && evt.shiftKey === false && sendOnEnter && text.length != 0) {
            console.log("Ho inviato:", text)
            //HandleSumbit(evt)
        }
    }

    const HandleSumbit = (evt) => {

        evt.preventDefault();

        var payload = {
            chatId: props.chatId,
            chatMessage: {
                "action": button,
                "sender": mainContext.user.characterName,
                "characterId": mainContext.user.characterId,
                "receiver": "",
                
                "img": mainContext.user.characterImg,
                "tag": tag,
                "testo": text,
                "timestamp": Date.now(),
                
            }
        }

        switch (button) {
            case "Parla":
                payload.chatMessage.testo = text;
                payload.chatMessage.receiver = '';
                sendMessage(payload);
                break;
            case "Dadi":

                var dadiPayload = {
                    "chatId": props.chatId,
                    "charachterId": mainContext.user.characterId,
                    "statName": value,
                    "img": mainContext.user.characterImg,
                    "sender": mainContext.user.characterName,
                    "tag": tag,
                }
                rollTheDice(dadiPayload);
                break;
            case "Sussurra":
                payload.chatMessage.tag ="";
                payload.chatMessage.receiver = nomeSussurro
                payload.chatMessage.testo = text;
                
                console.log("PayloadSussurro", payload)
                sendMessage(payload);
                break;
            case "Narra":
                payload.chatMessage.testo = text
                
                payload.chatMessage.receiver = ''
                sendMessage(payload);
                break;
            case "Attacca":
                payload.chatMessage.receiver = tag
                
                payload.chatMessage.receiver = ''
                //TODO chiamare funzione attacca
                break;
            default:
                break;
        }
    }

    const sendMessage = (payload) => {
        axios.post(API_URL.CHAT + "/addmessage", payload,{
            headers: {
              'Authorization': 'Fervm '+store.get('jwt')
            }})
            .then(onCompleteSumbit)
            .catch(err => {
                console.log("qualcosa non ha funzionato");
            })
    }

    const rollTheDice = (payload) => {
        axios.post(API_URL.CHAT + "/dice", payload,{
            headers: {
              'Authorization': 'Fervm '+store.get('jwt')
            }})
            .then(onCompleteSumbit)
            .catch(err => {
                console.log("qualcosa non ha funzionato");
            })
    }

    function onCompleteSumbit() {
        setText("");
        setDice(false);
        if (button === "Sussurra") setNomeSussurro("");
        setButton("Parla");

    }

    const handleChange = (evt) => {
        console.log("setValue : " + evt.target.value)
        setValue(evt.target.value)
    }

    const handleGroup = (event, newButton) => {
        setButton(newButton);
        if (newButton === "Dadi") setDice(true)
        else setDice(false);
        if (newButton === "Sussurra") getPresenti()
        else {setPlaceTag("Tag"); setNomeSussurro('')}
    };

    const setSussurro = event => {
        setNomeSussurro(event.target.value)
    };

    const getPresenti = () => {
        axios.get(API_URL.PRESENTI + "/presenti/chatId=" + props.chatId,{
            headers: {
              'Authorization': 'Fervm '+store.get('jwt')
            }})
            .then(resp => {
                setPresentiChat(resp.data.presentiChat)
            })
    }

    const tornaControllo = () => {
        var controllo =
            <div>
                <TextField className="tagLuogo" size="small" type="text" placeholder={placeTag} value={tag} onChange={HandleTagChange} />
                <textarea className="text" type="textarea" placeholder="Inserisci la tua azione" value={text} onChange={HandleTextChange} onKeyDown={submitMessage} />
            </div>
        switch (button) {
            case "Parla":
                controllo =
                <div>
                    <TextField className="tagLuogo" size="small" type="text" placeholder={placeTag} value={tag} onChange={HandleTagChange}  />
                    <textarea className="text" type="textarea" placeholder="Inserisci la tua azione" value={text} onChange={HandleTextChange} onKeyDown={submitMessage} />
                </div>
                break;
            case "Sussurra":
                controllo = <div>
                <FormControl>
                    <InputLabel htmlFor="age-helper">Nome PG</InputLabel>
                    <Select
                        style={{ minWidth: '200px' }}
                        value={nomeSussurro}
                        input={<Input name = "name" id="age-helper" />}
                        onChange={setSussurro}>
                        {presentiChat.map((opt) => {
                            return (
                                <MenuItem key={opt.charachterId} value={opt.characterName} name={opt.characterName}>{opt.characterName}</MenuItem>
                            );
                        }
                        )}
                    </Select>
                </FormControl>
                    <textarea className="text" type="textarea" placeholder="Inserisci la tua azione" value={text} onChange={HandleTextChange} onKeyDown={submitMessage} />
                    </div>
                break;
            case "Dadi":
                controllo=<div>
                    <RadioGroup aria-label="Caratteristiche" name="Dadi" value={value} onChange={handleChange}>
                        <FormControlLabel value="DESTREZZA" control={<Radio />} label="Destrezza" />
                        <FormControlLabel value="COSTITUZIONE" control={<Radio />} label="Costituzione" />
                        <FormControlLabel value="INTELLIGENZA" control={<Radio />} label="Intelligenza" />
                        <FormControlLabel value="FORZA" control={<Radio />} label="Forza" />
                        <FormControlLabel value="SAGGEZZA" control={<Radio />} label="Saggezza" />
                    </RadioGroup>
                </div>
                break;
            case "Narra":
                controllo = <textarea className="text" type="textarea" placeholder="Inserisci la tua azione" value={text} onChange={HandleTextChange} onKeyDown={submitMessage} />
                break;
            case "Attacca":
                controllo = <div>Coming Soon!</div>
                break;
            default:
                controllo =
            <div>
                <TextField className="tagLuogo" size="small" type="text" placeholder={placeTag} value={tag} onChange={HandleTagChange} />
                <textarea className="text" type="textarea" placeholder="Inserisci la tua azione" value={text} onChange={HandleTextChange} onKeyDown={submitMessage} />
            </div>
        }

        return controllo;
    }

    return (
        <form onSubmit={HandleSumbit}>
            <div className="tasti-group">
                <ToggleButtonGroup size="medium" variant="contained" value={button} exclusive onChange={handleGroup} aria-label="contained primary button group">
                    <ToggleButton value="Parla" className='ctrl-btn-S'>
                        Parla
                    </ToggleButton>
                    <ToggleButton value="Dadi" className='ctrl-btn-S'>
                        Dadi
                    </ToggleButton>
                    {(mainContext.roles.includes("ROLE_NARRATOR") || mainContext.roles.includes("ADMIN")) ? <ToggleButton value="Narra" className='ctrl-btn-S'>Narra </ToggleButton> : null}
                    <ToggleButton value="Attacca" className='ctrl-btn-M' >
                        Attacca
                    </ToggleButton>
                    <ToggleButton value="Sussurra" className='ctrl-btn-M'>
                        Sussurra
                    </ToggleButton>
                </ToggleButtonGroup>
            </div>
            {
                tornaControllo()
            }
            <div className="send">
                <div style={{ color: text.length > 900 ? 'orange' : 'inherit', maxWidth: '40%' }}>{text.length} Caratteri
                    <div>{text.length > 900 && <>
                        <small style={{ color: 'orange' }}>La tua azione è lunga. Accorciala per velocizzare il turno e guadagnare più punti esperienza</small></>}
                    </div>
                </div>
                <div>
                    <label>premi 'Invio' per inviare</label>
                    <Checkbox color="primary" checked={sendOnEnter} onChange={() => { setSendOnEnter(!sendOnEnter) }} inputProps={{ 'aria-label': 'uncontrolled-checkbox' }}></Checkbox>
                    <button className='main-btn main-btn-M' type="submit" disabled={!text && !dice}>
                        Invia
                    </button>
                </div>
            </div>
        </form>
    )
}

export default ControlliChat
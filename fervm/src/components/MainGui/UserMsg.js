import { TextField } from '@material-ui/core'
import axios from 'axios'
import React, { useState } from 'react'
import { API_URL } from '../../utils/api';
import store from 'store';


/**
 * Set the online message of the characher and modify the Characher image based on what you pass on the prop list
 * @param {image (to change the image) , message (to change online message) , 
 * setPg to modify the Character and re-render the component} props 
 * @returns 
 */
function UserMsg (props){

    const [msg, setMsg] = useState(props.type === 'message' ? "" : props.oldImg);
    
    const handleSubmit  = (event) =>{
        event.preventDefault();
        /* inviare il messaggio qui */
        
        switch(props.type){
            case "image":
                var payload = {
                    charachterId: props.id,
                    urlImg: msg
                }    
                
                axios.patch(API_URL.CHARACTER + "/updateimage",payload ,{
                    headers: {
                      'Authorization': 'Fervm '+store.get('jwt')
                    }})
                props.setPg(prevPg =>({
                    ...prevPg,
                    characterImg: msg
                }) )
                break;
            case "message":
                var payload = {
                    characterId: props.id,
                    message: msg
                }
                axios.patch(API_URL.PRESENTI + "/updatemessage",payload , {
                    headers: {
                      'Authorization': 'Fervm '+store.get('jwt')
                    }})
                break;
            default:
                break;
        }
        setMsg("")
    }

    const messageChange = (event) =>{
        setMsg(event.target.value);
    }

    return(
        <>
        <label>{props.type === 'image' ? "Cambia Immagine di Profilo": "Imposta un messaggio di stato"}</label>
        <form style={{textAlign:"center", margin:"0px 0px 0px 0px", display:"flex",alignItems:"center"}} onSubmit={handleSubmit}>
          <TextField size="small" type="text" placeholder={props.type==='image' ? 'url immagine Profilo': 'Messaggio di stato'} value={msg} onChange={messageChange}  />
          <button type="submit" className='ctrl-btn-S'>invia</button>
        </form>
        </>
    )
} export default UserMsg;
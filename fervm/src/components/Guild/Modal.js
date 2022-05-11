import React, {useState,useContext} from 'react'
import {Select, MenuItem,Input,FormControl,InputLabel } from '@material-ui/core'
import './Modal.css'
import {userContext} from '../../utils/userContext';
import axios from 'axios';
import { API_URL, getJwt } from '../../utils/api';
import useMessage from '../../utils/useMessage';

function Modal(props){
  
    const [charId, setCharId] = useState()
    const [nome, setNome] = useState('...')
    const { addMessage } = useMessage();
    const mainContext = useContext(userContext);

    if(!props.open){
        return null;
    }


    const handleChange = event => {
        console.log("FIRE!!", event.target)
        setCharId(event.target.value);
        setNome([event.target.value])
       };

    const arruolaPg = ()=>{
        var paylod = {
            character_id: charId,
            role_id : props.roleId,
            executorId : mainContext.user.characterId
        }
        
        axios.post(API_URL.GUILD+"/members/addmember", paylod , {
            headers: {
              'Authorization': 'Fervm '+getJwt()
            }})
        .then(resp=>{
            //console.log(resp.data.added)
            if(resp.data.added){
                props.setFresh(false)
                props.chiudi()
            
            }
            else{
                addMessage(resp.data.message)
            }
        })
        .catch(err=>{
            console.log(err)
        })
    }

    return(
        <div className="modal">
            <div className="modal-content">
                <div className="modal-header">
                    <h4>{props.title}</h4>
                </div>
                <div className="modal-body">
                {props.lista.length > 0 ? <FormControl>
                <InputLabel htmlFor="age-helper">Nome PG</InputLabel>
                <Select
                style={{minWidth:'200px'}}
                    value = {nome}
                    input={<Input  name="name" id="age-helper" />}
                    onChange = {handleChange}
                >
                    
                    {props.lista.map((opt)=>{
                        return(
                            <MenuItem value={opt.characterId} name={opt.characterName}>{opt.characterName+' '+opt.characterSurname}</MenuItem>
                        );}
                    )}
                </Select>
                </FormControl> : <p>Non ci sono personaggi disoccupati</p>}
                </div>
                <div className="modal-footer">
                    {props.lista.length > 0 && <button className='primary-btn-M' onClick={arruolaPg}>Arruola</button>}
                    <button className='primary-btn-M' onClick={props.chiudi} >Close</button>
                </div>
            </div>

        </div>
    )
} export default Modal;
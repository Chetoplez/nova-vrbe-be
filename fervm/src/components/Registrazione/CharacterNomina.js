import { TextField } from "@material-ui/core";

import React from "react";


function CharacterNomina({ errors, register , checkNome , validName }){
  
    return(
        <div style={{ marginTop: '30px' }} >
                <h4>Inserisci il tuo Prenomen</h4>
                <div style={{ display: 'flex', alignItems: 'end', justifyContent: 'center' }}>
                    <TextField
                       
                        placeholder="Inserisci il tuo Prenomen"
                        name="characterName"
                        {...register("characterName", { 
                            required: {value:true, message: 'Questo campo è obbligatorio'},
                            minLength: {value: 3, message: 'il Prenome deve essere almeno 3 caratteri'},
                            maxLength: {value: 25, message: 'Il Prenome non deve superare i 25 caratteri'}})}
                     />
                    {/* <IconButton
                        style={{ borderRadius: '50%', backgroundColor: '#db872a', marginLeft: '20px' }}>
                        <CasinoOutlinedIcon style={{ color: 'white' }}></CasinoOutlinedIcon>
                    </IconButton> */}
                </div>
                <p className="errorMessage">{errors.characterName?.type === 'required' ? errors.characterName.message : ''}</p>
                <p className="errorMessage">{errors.characterName?.type === 'minLength' ? errors.characterName.message : ''}</p>
                <p className="errorMessage">{errors.characterName?.type === 'maxLength' ? errors.characterName.message : ''}</p>
                <h4>Inserisci il tuo Cognomen</h4>
                <TextField
                               
                    placeholder="Inserisci il Cognomen"
                    name="characterCognomen"
                    {...register("characterCognomen", { 
                            required: {value:true, message: 'Questo campo è obbligatorio'},
                            minLength: {value: 3, message: 'il Cognomen deve essere almeno 3 caratteri'},
                            maxLength: {value: 25, message: 'Il Cognomen non deve superare i 25 caratteri'}} )} />
                <p className="errorMessage">{errors.characterCognomen?.type === 'required' ? errors.characterCognomen.message : ''}</p>
                <p className="errorMessage">{errors.characterCognomen?.type === 'minLength' ? errors.characterCognomen.message : ''}</p>
                <p className="errorMessage">{errors.characterCognomen?.type === 'maxLength' ? errors.characterCognomen.message : ''}</p>
                <p><button className="ctrl-btn-M" type="button" onClick={()=>{checkNome()}}>Verifica</button></p>
                <p>Questo nome {validName ? <span style={{color:"#63C121"}}>è disponibile</span> : <span style={{color:"#A74531"}}>non è disponibile</span>}</p>           
            </div>
    )
} export default CharacterNomina;
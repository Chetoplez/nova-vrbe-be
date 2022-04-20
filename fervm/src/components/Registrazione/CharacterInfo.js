import { TextField, IconButton } from "@material-ui/core";
import React from "react";
import CasinoOutlinedIcon from '@material-ui/icons/CasinoOutlined';
import './CharacterInfo.css';

function CharacterInfo({ register, setGender, errors }) {

    return (
        <><h4>Scegli il genere del tuo personaggio</h4>
            <div style={{ marginTop: '30px' }}>
                {/* <label htmlFor="radio-m" className='ctrl-btn-M'>
                    <input type="radio" name="gender" value={"MASCHIO"} id="radio-m"
                        {...register("gender")} />
                    Dominus
                </label>

                <label htmlFor="radio-f" className='ctrl-btn-M'>
                    <input type="radio" name="gender"
                        value={"FEMMINA"} id="radio-f"
                        {...register("gender")} /> Domina
                </label> */}
                <select style={{padding:'10px'}} defaultValue={"FEMMINA"} {...register("gender")}>
                    <option style={{padding:'10px'}} value={"MASCHIO"}>Dominus</option>
                    <option value={"FEMMINA"}>Domina</option>                    
                </select>
            </div>
            <div style={{ marginTop: '30px' }} >
                <h4>Inserisci il tuo Nomen</h4>
                <div style={{ display: 'flex', alignItems: 'end', justifyContent: 'center' }}>
                    <TextField
                        placeholder="Inserisci il tuo Nomen"
                        name="characterName"
                        {...register("characterName", { 
                            required: {value:true, message: 'Questo campo è obbligatorio'},
                            minLength: {value: 3, message: 'il Nome deve essere almeno 3 caratteri'},
                            maxLength: {value: 25, message: 'Il nome non deve superare i 25 caratteri'}})}
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
                            minLength: {value: 3, message: 'il Nome deve essere almeno 3 caratteri'},
                            maxLength: {value: 25, message: 'Il nome non deve superare i 25 caratteri'}} )} />
                <p className="errorMessage">{errors.characterCognomen?.type === 'required' ? errors.characterCognomen.message : ''}</p>
                <p className="errorMessage">{errors.characterCognomen?.type === 'minLength' ? errors.characterCognomen.message : ''}</p>
                <p className="errorMessage">{errors.characterCognomen?.type === 'maxLength' ? errors.characterCognomen.message : ''}</p>
            </div>
            </>
    )
} export default CharacterInfo;
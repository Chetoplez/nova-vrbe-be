import { TextField } from "@material-ui/core";
import React from "react";
//import CasinoOutlinedIcon from '@material-ui/icons/CasinoOutlined';
import './CharacterInfo.css';

function CharacterInfo({ register, errors }) {

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
            <div style={{ marginTop: '30px' }}>
            <h4>Scegli lo status del tuo Personaggio</h4>
                <select style={{padding:'10px'}} defaultValue={"PLEBEO"} {...register("status")}>
                    <option style={{padding:'10px'}} value={"PLEBEO"}>Plebeo</option>
                    <option value={"SCHIAVO"}>Schiavo</option>                    
                </select>
            </div>
                        
            </>
    )
} export default CharacterInfo;
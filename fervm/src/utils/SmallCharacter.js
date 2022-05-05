import React from "react";
import Avatar from "@material-ui/core/Avatar";
import {Link} from 'react-router-dom';

function SmallCharacter ({ character }){
    return(
        <div>
            <div style={{display:'flex', alignItems:'center'}}>
               <Avatar src={character.characterImg} ></Avatar>
              
                   <Link to={"/game/profilo/"+character.characterId}><p>{character.characterName + ' ' + character.characterSurname}</p></Link>
               
            </div>
        </div>
    )
}export default SmallCharacter;
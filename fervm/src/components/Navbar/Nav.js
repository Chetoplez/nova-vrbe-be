import React, { useContext } from "react";

import InfoLuogo from './InfoLuogo.js';
import '../Navbar/Nav.css';
import UserBtn from './UserBtn';
import HambIcon from '../../img/hamburgerIcon.png'
import { Link } from 'react-router-dom';
import IconButton from '@material-ui/core/IconButton';
import {userContext} from '../../utils/userContext'
import MissivaButton from "./MissivaButton.js";

/**
 * The game navigation bar. 
 * 
 * @param {idLuogo, to set up the place wher the pg is} props 
 * @returns 
 */
function Nav(props) {
  const mainContext = useContext(userContext);
    const myFunction = ()=> {
        var x = document.getElementById("demo");
        if (x.className.indexOf("w3-show") === -1) {
          x.className += " w3-show";
        } else { 
          x.className = x.className.replace(" w3-show", "");
        }
      }
      
    

    return (
        <div className='container'>
            <div className='chat-side w3-half'>
                <span className="w3-header game-name">FERVM</span>
                <InfoLuogo idLuogo={props.idLuogo} />
            </div>
            <div className='cmd-side w3-half'>
                <div className="w3-bar-item w3-hide-small"><Link to='/game/mainmap'>Mappa</Link></div>
                <div className="w3-bar-item w3-hide-small"><Link to="/game/forum">Bacheca</Link></div>
                <div className="w3-bar-item w3-hide-small"><Link to="/game/corporazioni">Corporazioni</Link></div>
                {/* <div className="w3-bar-item w3-hide-small"><Link to="#"><img className="icon-class" src={MissivaIcon} alt="missiva" ></img></Link></div> */}
                <div className="w3-bar-item w3-hide-small"><Link to="/game/missive"><MissivaButton chId = {mainContext.user.characterId} /></Link></div>
                
                <div className="w3-bar-item w3-hide-small"><UserBtn /></div>
                <div className="w3-hide-large w3-hide-medium">
                  <IconButton className="w3-bar-item w3-button w3-right w3-hide-large w3-hide-medium" onClick={myFunction}>
                  <span><img alt="ham-menu" style={{width:'24px'}} src={HambIcon}></img></span></IconButton>
                </div>
            </div>
            <div id="demo" className='w3-bar-block w3-white w3-hide w3-hide-large w3-hide-medium'>
                <div><Link to='/game'>MAPPA</Link></div>
                <div><Link to="#">BACHECA</Link></div>
                <div><Link to="#">MISSIVE</Link></div>
                <div><Link to={"/game/profilo/"+mainContext.user.characterId}>{mainContext.user.characterName}</Link></div>
            </div>
         </div>
        
    );
}

export default Nav
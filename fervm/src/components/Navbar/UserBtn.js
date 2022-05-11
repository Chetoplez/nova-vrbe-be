import  React, { useContext, useState } from "react";
import '../Navbar/UserBtn.css'
import {userContext} from '../../utils/userContext';
import {Link} from 'react-router-dom'
import { Avatar, makeStyles } from "@material-ui/core";

import axios from 'axios';
import {API_URL, getJwt} from '../../utils/api' 



const useStyles = makeStyles({
    img:{
       borderRadius: '50%'
     },
     root: {
         width: '100px',
         height: '100px',
         padding: '15px'
     }
   });


function UserBtn() {
    const mainContext = useContext(userContext);
    const [menuOpen, setMenuOpen] = useState('');
    const classes = useStyles()
    const openMenu = ()=>{
        if(menuOpen==='')
            setMenuOpen('openEffect');
        else setMenuOpen('')
    }

    const logout = ()=>{
        axios.patch(API_URL.PRESENTI+"/getonline",
        {characterId: mainContext.user.characterId, online: false},
        {
            headers: {
                Authorization: "Fervm "+getJwt()
            }
        }).then(resp=>{
            if(resp.data.changed)
                mainContext.tryLogout()
        })
        
      }

    return(
        <>
        <div className='pg-name' onClick={openMenu}>
            {/* <div><strong>{props.pg.nome}</strong></div> */}
            {/* <img className="user-profile-mini" src={mainContext.user.characterImg} alt="immaginetta profilo"></img> */}
            <Avatar className="usr-btn" src={mainContext.user.characterImg}  classes={classes}/>
        </div>
        
            <div id="menupg" className={menuOpen+' '}>
                <ul>
                    <li><Link to={"/game/profilo/"+mainContext.user.characterId} onClick={openMenu}>Profilo</Link></li>
                    {mainContext.isLoggedIn && (<li><Link to="#" onClick={logout}>Logout</Link></li>)}
                </ul>
            </div>
        
        </>
    );
}

export default UserBtn
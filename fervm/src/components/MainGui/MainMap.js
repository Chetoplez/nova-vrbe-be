import React, { useEffect,useContext,useState } from 'react'
import {useNavigate,Link} from 'react-router-dom'
import '../MainGui/MainMap.css';

import "react-svg-map/lib/index.css";
import ElencoPresenti from './ElencoPresenti';
import axios from 'axios'
import {API_URL, getJwt} from '../../utils/api'
import { userContext } from '../../utils/userContext'
import store from 'store'
import mappa from '../../img/testMappa.jpg';

/**
 * Temporary map of the game. 
 * This will be a mess... i arrange this with two dots only for testing the chats
 */
function MainMap(props) {
   
    const navigate = useNavigate();
    const mainContext = useContext(userContext);
        
    useEffect(()=>{
        const checkAuth = async () => {
            let res = mainContext.tryLoginUser()
            if (res) {
                
            } else {
                console.log("beffato!")
                // redirect to login page non aha 
                navigate("/")
            }
        }

        checkAuth();
        document.title = 'Fervm GdR - Mappa'
        props.setIdLuogo(0)
        var payload ={
            characterId: mainContext.user.characterId,
            idLuogo: 0
        }
        
        axios.patch(API_URL.PRESENTI+"/getonline",{characterId: mainContext.user.characterId,online: true},{
            headers: {
              'Authorization': 'Fervm '+getJwt()
            }})
        axios.patch(API_URL.PRESENTI+"/moveto", payload,{
            headers: {
              'Authorization': 'Fervm '+getJwt()
            }})

    },[])


    return(
    <div className="vrbe-mainMap">
       
         <div className="map contenitori">
            <div>
                <Link to="/game/chat/1"><span className="dot"></span></Link>
                <Link to="/game/chat/2"><span className="dot1"></span></Link>
            </div>
            
        </div>  
        
        <ElencoPresenti className="w3-quarter" />
    </div>
    )
}

export default MainMap
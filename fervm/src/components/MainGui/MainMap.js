import React, { useEffect,useContext,useState } from 'react'
import {useNavigate,Link} from 'react-router-dom'
import '../MainGui/MainMap.css';


import ElencoPresenti from './ElencoPresenti';
import axios from 'axios'
import {API_URL, getJwt} from '../../utils/api'
import { userContext } from '../../utils/userContext'
import { Tooltip } from '@material-ui/core';
import mappa from '../../img/main_map.png'


/**
 * Temporary map of the game. 
 * This will be a mess... i arrange this with two dots only for testing the chats
 */
function MainMap(props) {
   
    const navigate = useNavigate();
    const mainContext = useContext(userContext);
    const [dimension, setDimension] = useState({height: 0, width: 0})
    
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
    },[])

    useEffect(()=>{
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


    const getDimension = ({target : img}) =>{
            //console.log("suca",img.offsetHeight)
            setDimension({height:img.offsetHeight,
                width:img.offsetWidth})
    }


    return(
    <div className="vrbe-mainMap">
       
         {/* <div className="map contenitori"  onLoad={getDimension}>
        
            <div>
                <Link to="/game/chat/1"><Tooltip title="Forum"><span className="dot dot0"></span></Tooltip></Link>
                <Link to="/game/chat/2"><Tooltip title="Porta Decumana"><span className="dot dot1"></span></Tooltip></Link>
                <Link to="/game/chat/3"><Tooltip title="Templum"><span className="dot dot2"></span></Tooltip></Link>
                <Link to="/game/chat/4"><Tooltip title="Taberna"><span className="dot dot3"></span></Tooltip></Link>
            </div>

            
        </div>   */}
         
         <div className='contenitori' style={{position: 'relative', textAlign:'center'}}>
            <div>
            <img style={{maxWidth:'85%', maxHeight: '80%'}} src={mappa} onLoad={getDimension} onChange={getDimension} />
            
            <Link to="/game/chat/1"><Tooltip title="Forum"><span className="dot" style={{top:(dimension.height/100)*42 , left:(dimension.width/100)*52 }}></span></Tooltip></Link>
            <Link to="/game/chat/2"><Tooltip title="Porta Decumana"><span className="dot" style={{top:(dimension.height/100)*27 , left:(dimension.width/100)*58 }}></span></Tooltip></Link>
            <Link to="/game/chat/3"><Tooltip title="Templum"><span className="dot" style={{top:(dimension.height/100)*26 , left:(dimension.width/100)*28 }}></span></Tooltip></Link>
            <Link to="/game/chat/4"><Tooltip title="Taberna"><span className="dot" style={{top:(dimension.height/100)*44 , left:(dimension.width/100)*55 }}></span></Tooltip></Link>
            </div>
        </div> 
        
        
        <div className='colonna-dx'><ElencoPresenti  /></div>
    </div>
    )
}

export default MainMap
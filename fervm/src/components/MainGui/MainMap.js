import React, { useEffect,useContext,useState, useRef } from 'react'
import {useNavigate,Link} from 'react-router-dom'
import '../MainGui/MainMap.css';


import ElencoPresenti from './ElencoPresenti';
import axios from 'axios'
import {API_URL, getJwt} from '../../utils/api'
import { userContext } from '../../utils/userContext'

import ChatDot from './ChatDot';
import mappa from '../../img/main_map.png'


/**
 * Temporary map of the game. 
 * 
 */
function MainMap(props) {
   
    const navigate = useNavigate();
    const mainContext = useContext(userContext);
    const [dimension, setDimension] = useState({height: 0, width: 0})
    const [chatList, setChatList] = useState([{}])
    const [isLoading , setLoading]=useState(true);
    const divRef = useRef();
    
    const getDimension = () =>{
        
        const newWidth = divRef.current.clientWidth;
        const newHeight = divRef.current.clientHeight;
        setDimension({height:newHeight, width:newWidth})
    }
    

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
        window.addEventListener("resize", getDimension);
        
        axios.get(API_URL.CHAT+'/chatlist' ,  {
            headers: {
                'Authorization': 'Fervm ' + getJwt()
            }
        }).then(resp=>{
            // console.log(resp.data.chatList)
            setChatList(resp.data.chatList)
            setLoading(false)
            getDimension()
        })

        return () => window.removeEventListener("resize");
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


    if (isLoading) {
        return <div className="Loading w3-card w3-center">Loading...</div>;
    }

    return(
    <div className="fervm-mainMap">
       
         <div className='map contenitori' style={{position: 'relative', textAlign:'center'}} ref={divRef}>
            <div >
                {
                    chatList.map((chat)=>{
                        return (<ChatDot key={chat.chatId} chat={chat} heigt={dimension.height} width={dimension.width} />)
                    })
                }            
            </div>
        </div>         
        <div className='colonna-dx'><ElencoPresenti  /></div>
    </div>
    )
}

export default MainMap
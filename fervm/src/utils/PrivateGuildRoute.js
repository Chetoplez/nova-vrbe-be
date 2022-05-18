
import axios from 'axios';
import React,{useContext} from 'react';
import { Navigate, useParams } from 'react-router-dom';
import { API_URL, getJwt } from './api';
import { userContext } from './userContext'
import useMessage from './useMessage';

/**
Verify if you are a Master of the guild
 */
const PrivateGuildRoute = ({ children }) => {
    const mainContext = useContext(userContext);
    let { idGilda } = useParams()
    const { addMessage } = useMessage();
    
    function isMasterOfGuild(){
        var master = false;
        var payload = {
            "characterId": mainContext.user.characterId,
            "guildId": idGilda
        }
        axios.post(API_URL.GUILD+"/members/checkguildpermission", payload,{
            headers: {
              'Authorization': 'Fervm '+getJwt()
            }})
        .then((resp)=>{ 
            master = resp.data.manager;
            return master;
        }).catch(err=>{
            console.log("guildPerm something went bad...  :(",err)
        })
    }

    if( !isMasterOfGuild()){
        addMessage("Non sei autorizzato qui")
        return <Navigate to={"/game/corporazioni/"+idGilda} ></Navigate>
    }

    return children
    
};

export default PrivateGuildRoute;

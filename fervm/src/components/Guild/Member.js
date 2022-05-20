import React, {useContext, useEffect, useState} from 'react';
import axios from 'axios';
import { Link, useParams } from 'react-router-dom';
import { IconButton, Tooltip } from '@material-ui/core';

import DoubleArrowOutlinedIcon from '@material-ui/icons/DoubleArrowOutlined';
import CancelOutlinedIcon from '@material-ui/icons/CancelOutlined';

import useMessage from '../../utils/useMessage';
import {userContext} from '../../utils/userContext'
import {API_URL, getJwt} from '../../utils/api';

function Member({ member , setFresh  }) {
    
    //console.log(mem);
    const [isManager, setManager] = useState(false)
    const mainContext = useContext(userContext)
    const { addMessage } = useMessage();
    let {idGilda } = useParams()

    useEffect(()=>{
        var payload = {
            "characterId": mainContext.user.characterId,
            "guildId": idGilda
        }
        axios.post(API_URL.GUILD+"/members/checkguildpermission", payload,{
            headers: {
              'Authorization': 'Fervm '+getJwt()
            }})
        .then((resp)=>{ 
            var permission = resp.data;
            setManager(permission.manager);
        }).catch(err=>{
            console.log("guildPerm something went bad...  :(",err)
        }) 
    },[])

    const promoteMember = ()=>{
        var payload = {
            character_id: member.character_ID,
            guild_id : member.guild_ID,
            executorId: mainContext.user.characterId
        }

        axios.patch(API_URL.GUILD+"/members/promote", payload,{
            headers: {
              'Authorization': 'Fervm '+getJwt()
            }})
        .then(resp=>{
            if(resp.data.promoted){
            setFresh(prev=> setFresh(!prev))
        }else{
            addMessage(resp.data.message)
            setFresh(prev=> setFresh(!prev))
        }
            
        })
        .catch(err=>{
            console.log(err)
        })
        
    }

    const degradeMember = ()=>{
        var payload = { 
            character_id: member.character_ID,
            guild_id : member.guild_ID,
            executorId: mainContext.user.characterId
        }

        axios.patch(API_URL.GUILD+"/members/degradate", payload,{
            headers: {
              'Authorization': 'Fervm '+getJwt()
            }})
        .then(resp=>{
            setFresh(prev=> setFresh(!prev))
        })
        
    }

    const deleteMember = ()=>{
        var payload = {
            data:{
                character_id: member.character_ID,
                role_id : member.role_ID,
                executorId: mainContext.user.characterId },
            headers: {
                'Authorization': 'Fervm '+getJwt()
              }

        }
       
        axios.delete(API_URL.GUILD+"/members/deletemember", payload )
        .then(resp=>{
            setFresh(prev=> setFresh(!prev))
        })
        .catch(err=>{
            console.log(err)
        })
        
    }


    return(
        <div className='d-flex arruolati-tasti'>
            <div><strong><Link to={"/game/profilo/"+member.character_ID}> {member.character_NAME +' '+member.character_SURNAME}</Link></strong></div>
             <div>
                {((isManager||mainContext.roles.includes("ROLE_ADMIN") ) && member.guild_LEVEL !== 50) ? <Tooltip title="Promuovi" placement="top-start">
                    <IconButton onClick={promoteMember} style ={{color: 'goldenrod'}}>
                        <DoubleArrowOutlinedIcon style={{ transform: 'rotate(-90deg)' }} />
                    </IconButton>
                </Tooltip> : null}
                {( (isManager|| mainContext.roles.includes("ROLE_ADMIN")) && member.guild_LEVEL !== 10) ? <Tooltip title="Degrada" placement="top-start">
                    <IconButton onClick={degradeMember} style ={{color: '#4998ec'}}>
                        <DoubleArrowOutlinedIcon style={{ transform: 'rotate(90deg)' }} />
                    </IconButton>
                </Tooltip>: null}
                {(isManager || mainContext.roles.includes("ROLE_ADMIN") ) ? <Tooltip title="Allontana" placement="top-start">
                    <IconButton onClick={deleteMember} style ={{color: '#aa3232'}}>
                        <CancelOutlinedIcon />
                    </IconButton>
                </Tooltip> : null }
             </div>
        </div>
    )
} export default Member;
import React,{useContext, useEffect, useState} from 'react';
import axios from 'axios'
import {userContext} from '../../utils/userContext';
import {Avatar, makeStyles } from '@material-ui/core';
import store  from 'store';
import { API_URL, getJwt } from '../../utils/api';
import useMessage from '../../utils/useMessage';

const useStyles = makeStyles({
    guildBadge:{
       width: 55,
       height: 55,
     }
   });

/**
 * This section is only visible if you are enrolled in the guild (enrolled flag), and allow the head of Guild to manage the Guild Bank
 * (to be defined).
 * 
 * @param {enrolled, manager} props 
 * @returns 
 */
function GuildMemberSection(props) {
    const mainContext = useContext(userContext);
    const classes = useStyles()
    const [myRole, setMyRole] = useState(null)
    const [loading, setLoading] = useState(true);
    const { addMessage } = useMessage();

    useEffect(()=>{
        axios.get(API_URL.GUILD+"/members/getinfo="+mainContext.user.characterId, {
            headers: {
              'Authorization': 'Fervm '+getJwt()
            }})
        .then(resp=>{
            setMyRole(resp.data.member);
             //console.log(resp.data.member)
            setLoading(false);
        })
    },[props.isFresh])

    if(loading){
        return (<div className="loading w3-card">Loading..</div>)
    }

    const getStipendio = ()=>{
        var payload = {
            characterId: mainContext.user.characterId
        }
       axios.post(API_URL.GUILD+"/members/getsalary",payload,{
        headers: {
          'Authorization': 'Fervm '+store.get('jwt')
        }})
       .then(resp=> {
          
           addMessage(resp.data.message);
       }).catch(err=>{
           addMessage(err.data);
       })
    }

    return(
        <div>
            <h3>{myRole.character_NAME + ' ' + myRole.character_SURNAME}</h3>
            <div className="w3-row">
                <div className="w3-content w3-half" style={{display:"flex" , alignItems:"center"}}>
                    <Avatar src={myRole.role_IMG} className={classes.guildBadge}></Avatar>
                    <div>
                        <h3>{myRole.role_NAME}</h3>
                        <p>Salario : {myRole.rolesalary} sz / giorno</p>
                    </div>
                </div>
                <div className="w3-content d-flex" style={{justifyContent:'space-between'}}>
                    <p>{(mainContext.roles.includes("ROLE_ADMIN") || props.manager) && (<button className='main-btn-M'>Banca</button>)}</p>
                    <p>{props.enrolled && (<button onClick={getStipendio} className='main-btn-M'>Ritira Paga</button>)}</p>
                </div>
            </div>
            
        </div>
    )
}export default GuildMemberSection;
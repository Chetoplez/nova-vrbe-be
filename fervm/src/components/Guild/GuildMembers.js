import React, { useEffect, useState, useContext } from 'react';
import { makeStyles, IconButton, Avatar, Tooltip } from '@material-ui/core'
import axios from 'axios';
import { API_URL, getJwt } from '../../utils/api';
import Member from './Member'
import { userContext } from '../../utils/userContext';
import './GuildMembers.css'
import AddCircleOutlineIcon from '@material-ui/icons/AddCircleOutline';
import Modal from './Modal'
import store from 'store';


const useStyles = makeStyles({
   guildBadge:{
      width: 55,
      height: 55,
    }
  });


function GuildMembers(props) {
    const classes = useStyles()
    const [members, setMembers] = useState([]);
    const [loading,setLoading] = useState(true);
    const [unemployed, setUnenmployed] = useState([])
    const mainContext = useContext(userContext);
    const [open, setOpen] = useState(false);
    const [roleId, setRoleId] = useState(0)
   
    
    var roles =  [].concat(props.roleList)
    .sort((a, b) => a.guild_level < b.guild_level ? 1 : -1);

    
    useEffect(()=>{
        document.title = 'Fervm GdR - Corporazioni'
        axios.get(API_URL.GUILD+"/members/guildid="+props.idGilda,{
            headers: {
              'Authorization': 'Fervm '+getJwt()
            }})
        .then(resp=>{
            setMembers(resp.data.members)
            //console.log(resp.data.members)
            setLoading(false);
        }).catch(err=>{
            console.log("something went wront on members",err)
        })

        

    },[props.isFresh])

    if(loading){
        return(<div className="loading">Loading</div>)
    }

    const getRoleMember = (roleId) =>{
       var _tmp = [];
        _tmp = members.filter((item)=> item.role_ID === roleId)
        .map((member,index)=>(
            <Member key={index} member={member} setFresh={props.setfresh}/>
        ));
        return _tmp;
    }
    
    
    const addNewMember = (idRuolo) => {
       axios.get(API_URL.CHARACTER+"/getcharacter/unemployed",{
        headers: {
          'Authorization': 'Fervm '+store.get('jwt')
        }})
       .then(resp=>{
            //console.log("Unemployed: ", resp.data.unemployed)       
            setUnenmployed(resp.data.unemployed);
            setRoleId(idRuolo)
            setOpen(true);
       })
       

    }

    return(
        <div>
       
            {roles.map((role,index)=>{
                    return( 
                    <div key = {index} className="roleBox">
                        <div style={{display:'flex', alignItems:'center'}}>
                            <Avatar src={role.role_img} alt={role.name} className={classes.guildBadge}></Avatar>
                            <Tooltip 
                                title={<div style={{fontSize:'14px', padding:'15px', borderRadius: '5px'}}>{role.description}</div>}
                                placement="top"
                                
                                >
                                <h4>{role.name}</h4>
                            </Tooltip>
                            {(  role.guild_level === 10 && props.isManager) || 
                                (role.guild_level === 10 && mainContext.roles.includes("ROLE_ADMIN") ) ? 
                                <IconButton onClick={()=>addNewMember(role.role_id)} style ={{color: '#00b300'}}><AddCircleOutlineIcon /></IconButton> : null}
                        </div>
                        <div  style={{display:'block', marginLeft:'45px', lineHeight:'2'}}>
                            {getRoleMember(role.role_id)}
                        </div>
                    </div> )
                })
            }
            <Modal 
                open={open}
                chiudi= {()=>setOpen(false)}
                title = "Personaggi Disoccupati"
                lista ={unemployed}
                roleId={roleId}
                setFresh={props.setfresh}
            />
            
        </div>
    )
} export default GuildMembers
import React , { useContext, useEffect, useState} from 'react'
import {useNavigate, useParams} from 'react-router-dom'
import {userContext} from '../../utils/userContext'
import axios from 'axios';
import store from  'store'
import { Button} from '@material-ui/core';
import './GuildPage.css'
import GuildMembers from './GuildMembers';
import GuildMemberSection from './GuildMemberSection';


import {API_URL} from '../../utils/api';

function GuildPage(props) {
    
    const [guildId, setGuildId] = useState(0);
    const [guild, setGuild] = useState({})
    const [loading, setLoading] = useState(true);
    const [isFresh, setIsFresh] = useState(true); //how can avoid the use of this barbarian method?????????????????
    const [enrolled,setEnrolled] =useState(false); //if i have to see the forum, or the announcement.
    const [manager, setManager] =useState(false); //if i can kick out, enroll, promote.. etc 
    const mainContext = useContext(userContext);// the character context
    const [roleList, setRoleList]=useState([{}])
    const history = useNavigate();
    let {idGilda } = useParams()
    
    useEffect(()=>{
        
        const checkAuth = async () => {
            let res =  mainContext.tryLoginUser()
            if(res){
                
            } else {
                // redirect to login page
                history("/")
            }
        }
        checkAuth();
        //I'm searching the local storage
        var id = store.get("guildId");
        if( id !== null )
            setGuildId(id);
        else{
            
            setGuildId(idGilda);
            store.set("guildId",idGilda);
        }

        var payload = {
            "characterId": mainContext.user.characterId,
            "guildId": idGilda
        }
        axios.post(API_URL.GUILD+"/members/checkguildpermission", payload,{
            headers: {
              'Authorization': 'Fervm '+store.get('jwt')
            }})
        .then((resp)=>{ 
            var permission = resp.data;
            setEnrolled(permission.present);
            setManager(permission.manager);
        }).catch(err=>{
            console.log("guildPerm something went bad...  :(",err)
        })

        axios.get(API_URL.GUILD+"/guildid="+idGilda,{
            headers: {
              'Authorization': 'Fervm '+store.get('jwt')
            }})
        .then(resp=>{
            setGuild(resp.data.guild)
            
        }).catch(err=>{
            console.log("Guild: network error ", err)
        })

        axios.get(API_URL.GUILD+"/role/guildid="+idGilda,{
            headers: {
              'Authorization': 'Fervm '+store.get('jwt')
            }})
        .then(resp=>{
            var roles =  [].concat(resp.data.guildRoleList)
            .sort((a, b) => a.guild_level < b.guild_level ? 1 : -1);
            setRoleList(roles);
            setIsFresh(true);
            setLoading(false);
        }).catch(err=>{
            console.log("something went wrong downloading guild's role")
        })

        document.title = "Fervm GdR - "+guild.name
        return () => {store.remove("guildId")}
    }, [guildId,isFresh])

    if(loading){
        return (<div className="loading w3-card">Loading..</div>)
    }


    return(
        <div className="guildContainer ">
            <div className="infoGuild colonna-sx">
                <div className='contenitori'>
                <div className="w3-half">
                    <div><h2>{guild.name}</h2></div>
                    <div>{guild.description}</div>
                </div>

                <div className="w3-half memberContainer">
                    <GuildMembers
                        roleList={roleList}
                        isManager={manager}
                        idGilda={idGilda}
                        setfresh={setIsFresh} isFresh={isFresh} />
                </div>
                </div>
            </div>
            {enrolled ?
                <div className="w3-half colonna-dx">
                    <section className='contenitori'>
                        <GuildMemberSection manager={manager} enrolled={enrolled} isFresh={isFresh} />
                    </section>
                </div> :
                <div className="w3-half colonna-dx">
                    <section className='contenitori'>
                        <h2>Struttura</h2>
                        <section className="announcement">
                            {guild.announcement}
                        </section>
                        <section className="salary">
                            {roleList.map((role, index) => {
                                return (
                                    <div key={index} style={{ display: 'flex', alignItems: 'center', padding: '10px' }}>
                                        <div style={{ width: '30%' }}><h3>{role.name}</h3>  </div>
                                        <div style={{ width: '50%' }}>{role.description} </div>
                                        <div style={{ width: '20%', textAlign: 'end' }}> {role.salary} sz</div>
                                    </div>)
                            })}
                        </section>
                        <button className='main-btn-M'>
                            Arruolati!
                        </button>
                    </section>
                </div>}
        </div>
        
    )
} export default GuildPage;
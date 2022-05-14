import React , { useContext, useEffect, useState} from 'react'
import {useNavigate, useParams} from 'react-router-dom'
import {userContext} from '../../utils/userContext'
import axios from 'axios';
import store from  'store'

import './GuildPage.css'
import GuildMembers from './GuildMembers';
import GuildMemberSection from './GuildMemberSection';



import {API_URL} from '../../utils/api';
import GuildRoleList from './GuildRoleList';

function GuildPage() {
    
    const [guildId, setGuildId] = useState(0);
    const [guild, setGuild] = useState({})
    const [loading, setLoading] = useState(true);
    const [isFresh, setIsFresh] = useState(true); //how can avoid the use of this barbarian method?????????????????
    const [enrolled,setEnrolled] =useState(false); //if i have to see the forum, or the announcement.
    const [manager, setManager] =useState(false); //if i can kick out, enroll, promote.. etc 
    const mainContext = useContext(userContext);// the character context
    
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
      //informazioni della gilda
        axios.get(API_URL.GUILD+"/guildid="+idGilda,{
            headers: {
              'Authorization': 'Fervm '+store.get('jwt')
            }})
        .then(resp=>{
            console.log(resp.data.guild)
            setGuild(resp.data.guild)
        }).catch(err=>{
            console.log("Guild: network error ", err)
        })
        document.title = "Fervm GdR - "+guild.name
        return () => {store.remove("guildId")}
    }, [guildId])

    useEffect(()=>{
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
            setLoading(false)
        }).catch(err=>{
            console.log("guildPerm something went bad...  :(",err)
        })

    },[isFresh])

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
                        isManager={manager}
                        idGilda={guild.id}
                        setFresh={setIsFresh}
                        isFresh={isFresh} />
                </div>
                </div>
            </div>
            {enrolled ?
                <div className="w3-half colonna-dx">
                    <section className='contenitori'>
                        <GuildMemberSection manager={manager} enrolled={enrolled} />
                    </section>
                </div> :
                <div className="w3-half colonna-dx">
                    <section className='contenitori'>
                        <h2>Struttura</h2>
                        <section className="announcement">
                            {guild.announcement}
                        </section>
                        <GuildRoleList idGilda={idGilda} />
                        <button className='primary-btn-M'>
                            Arruolati!
                        </button>
                    </section>
                </div>}
        </div>
        
    )
} export default GuildPage;
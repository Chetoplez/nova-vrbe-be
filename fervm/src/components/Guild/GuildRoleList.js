import React, { useState, useEffect } from "react";
import axios from "axios";
import { API_URL, getJwt } from "../../utils/api";

function GuildRoleList({ idGilda }){

    const [roleList , setRoleList] = useState([{}])
    const [loading , setLoading] = useState(true);

    useEffect(()=>{
        //Lista dei ruoli della gilda
        axios.get(API_URL.GUILD+"/role/guildid="+idGilda,{
            headers: {
              'Authorization': 'Fervm '+getJwt()
            }})
        .then(resp=>{
            var roles =  [].concat(resp.data.guildRoleList)
            .sort((a, b) => a.guild_level < b.guild_level ? 1 : -1);
            setRoleList(roles);
            setLoading(false)
            
        }).catch(err=>{
            console.log("something went wrong downloading guild's role")
        })

    },[])

    if(loading){
        return (<div className="loading w3-card">Loading..</div>)
    }

    return(
        <>
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
        </>
    )
} export default GuildRoleList;
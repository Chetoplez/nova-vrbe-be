import React, { useEffect, useState } from 'react'
import { API_URL, getJwt } from '../../utils/api';
import { axios } from 'axios';

function GuildStructure ({ guild }) {
    console.log("GuildStructure guild:", guild)
    const [roleList, setRoleList] = useState([{
        name:'',
        description: '',
        salary: ''
    }])
    const [loading, setLoading] = useState(true)

    useEffect(()=>{
        axios.get(API_URL.GUILD + "/role/guildid=" + guild.id, {
            headers: {
                'Authorization': 'Fervm ' + getJwt()
            }
        }).then(resp => {
            var roles = [].concat(resp.data.guildRoleList)
                .sort((a, b) => a.guild_level < b.guild_level ? 1 : -1);
            setRoleList(roles);
            setLoading(false);
        }).catch(err => {
            console.log("something went wrong downloading guild's role")
        })
    },[])

    if (loading) {
        return (<div className="loading w3-card">Loading..</div>)
    }

    return(
        <section className='contenitori'>
            <h2>{guild.name}</h2>
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
    )
}export default GuildStructure;
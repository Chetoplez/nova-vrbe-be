import { Avatar } from "@material-ui/core";
import React, { useContext, useEffect, useState } from "react";
import { Link } from 'react-router-dom';
import parse from 'html-react-parser';
import axios from "axios";
import { API_URL, getJwt } from "../../utils/api";
import { userContext } from "../../utils/userContext";
import './ForumCard.css'


function ForumCard({ forum , guild }) {

    const mainContext = useContext(userContext);
    const [loading, setLoading] = useState(true);
    const [characterdGuild, setCharactedGuild] = useState({})

    
    useEffect(()=>{
        axios.get(API_URL.GUILD+"/members/getinfo="+mainContext.user.characterId ,
        {
            headers:{
                Authorization: "Fervm "+getJwt()
            }
        })
        .then(resp=>{
            // console.log("Membro", resp.data.member)
            // console.log("Forum", forum)
            if(!resp.data.unenmployed){
            setCharactedGuild(resp.data.member)
            }
           
            setLoading(false)
        })
    },[])

    if (loading) {
        return <div className="App">Loading...</div>;
    }


    function renderCard() {
        if(!guild || mainContext.roles.includes("ROLE_ADMIN")){
            return(
                <div className="forum-card link-bacheca">
                    <div className="forum-card-body" >
                        <Avatar variant="square"/>
                        <div>
                            <Link className="bachechaLink" to={"subforums/" + forum.forumId}><h3>{forum.name}</h3></Link>
                            <div style={{whiteSpace: 'pre-wrap'}}>{parse(forum.description)}</div>
                        </div>
                        {forum.unread && (<span>NUOVI MESSAGGI</span>)}
                    </div>
            </div>
            )
        }else if(characterdGuild.guild_ID === forum.ownedBy){
            return (
                <div className="forum-card link-bacheca">
                    <div className="forum-card-body d-flex">
                        <Avatar variant="square" />
                        <div>
                            <Link className="bachechaLink" to={"subforums/" + forum.forumId}><h3>{forum.name}</h3></Link>
                            <div style={{ whiteSpace: 'pre-wrap' }}>{parse(forum.description)}</div>
                        </div>
                        {forum.unread && (<span>NUOVI MESSAGGI</span>)}
                    </div>
                    
                </div>
            )
        } else {
            return (
                <div className="forum-card">
                    <div className="forum-card-body" style={{color:'#554641', filter: 'grayscale(1)'}}>
                        <Avatar variant="square" />
                        <h3>{forum.name}</h3>
                    </div>
                    <div style={{ whiteSpace: 'pre-wrap' }}>{parse(forum.description)}</div>
                    
                </div>
            )
        }
    }

    return (
        <>
            { renderCard() }
        </>
    )
} export default ForumCard;
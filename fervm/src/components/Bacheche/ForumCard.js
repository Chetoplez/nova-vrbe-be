import { Avatar } from "@material-ui/core";
import React, { useContext, useEffect, useState } from "react";
import { Link } from 'react-router-dom';
import parse from 'html-react-parser';
import axios from "axios";
import { API_URL, getJwt } from "../../utils/api";
import { userContext } from "../../utils/userContext";


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
            
            setCharactedGuild(resp.data.member)
            //console.log(characterdGuild.guild_ID === forum.ownedBy)
            setLoading(false)
        })
    },[])

    if (loading) {
        return <div className="App">Loading...</div>;
    }


    function renderCard() {
        if(!guild || mainContext.roles.includes("ROLE_ADMIN")){
            return(
                <div className="w3-card w3-padding w3-margin">
                <div style={{display:'flex' , alignItems:'center', textTransform:'capitalize'}}>
                    <Avatar variant="square"/>
                    <Link to={"subforums/" + forum.forumId}><h3>{forum.name}</h3></Link>
                </div>
                <p style={{whiteSpace: 'pre-wrap'}}>{parse(forum.description)}</p>
            </div>
            )
        }else if(characterdGuild.guild_ID === forum.ownedBy){
            return (
                <div className="w3-card w3-padding w3-margin">
                    <div style={{ display: 'flex', alignItems: 'center', textTransform: 'capitalize' }}>
                        <Avatar variant="square" />
                        <Link to={"subforums/" + forum.forumId}><h3>{forum.name}</h3></Link>
                    </div>
                    <p style={{ whiteSpace: 'pre-wrap' }}>{parse(forum.description)}</p>
                </div>
            )
        } else {
            return (
                <div className="w3-card w3-padding w3-margin" style={{backgroundColor: 'lightgrey'}}>
                    <div style={{ display: 'flex', alignItems: 'center', textTransform: 'capitalize' }}>
                        <Avatar variant="square" />
                        <h3>{forum.name}</h3>
                    </div>
                    <p style={{ whiteSpace: 'pre-wrap' }}>{parse(forum.description)}</p>
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
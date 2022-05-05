import React, { useEffect, useState } from 'react'
import { Avatar , Badge } from "@material-ui/core";
import MissivaIcon from '../../img/buttons/Button-Circle-MessageOn.svg'
import axios from 'axios';
import { API_URL, getJwt } from '../../utils/api';

function MissivaButton ({ chId }) {
    const [newMissive, setNewMissive] = useState(0)

    useEffect(()=>{
        axios.get(API_URL.MISSIVE + '/checkinbox/'+chId ).then(resp=>{
            setNewMissive(resp.data.nnewMail)
        })


        const interval = setInterval(()=>{
            //console.log("MissivaButton")
            axios.get(API_URL.MISSIVE + '/checkinbox/'+chId ).then(resp=>{
                setNewMissive(resp.data.nnewMail)
            })
        },10000)

        
        return () => clearInterval(interval);
    }, [])

    return(
        <Badge badgeContent={newMissive} color="primary">
            <img width={55} src={MissivaIcon}></img>
        </Badge>
    )
} export default MissivaButton;
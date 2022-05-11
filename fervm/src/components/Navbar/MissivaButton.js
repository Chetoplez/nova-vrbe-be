import React, { useEffect, useState } from 'react'
import { Avatar , Badge } from "@material-ui/core";
import MissivaIconNormal from '../../img/buttons/special/messages/Button-Special-Messages-Default.svg'
import MissivaIconNewMessage from '../../img/buttons/special/messages/Button-Special-Messages-Active.svg'
import axios from 'axios';
import { API_URL, getJwt } from '../../utils/api';

function MissivaButton ({ chId }) {
    const [newMissive, setNewMissive] = useState(0)

    useEffect(()=>{
        axios.get(API_URL.MISSIVE + '/checkinbox/'+chId ,{
            headers: {
                'Authorization': 'Fervm ' + getJwt()
            }
        } ).then(resp=>{
            setNewMissive(resp.data.nnewMail)
        })


        const interval = setInterval(()=>{
            //console.log("MissivaButton")
            axios.get(API_URL.MISSIVE + '/checkinbox/'+chId ,  {
                headers: {
                  'Authorization': 'Fervm '+getJwt()
                }} ).then(resp=>{
                setNewMissive(resp.data.nnewMail)
            })
        },10000)

        
        return () => clearInterval(interval);
    }, [])

    return(
        <Badge badgeContent={newMissive} color="primary">
            <img width={55} src={newMissive > 0 ? MissivaIconNewMessage : MissivaIconNormal}></img>
        </Badge>
    )
} export default MissivaButton;
import React, { useEffect, useState } from "react";
import axios from "axios";
import Missiva from "./Missiva";
import './MissivaInbox.css';
import { API_URL, getJwt } from "../../utils/api";
import IconButton from '@material-ui/core/IconButton';
import DeleteIcon from '@material-ui/icons/Delete';
import { Tooltip } from '@material-ui/core';
import MarkEmailReadIcon from '@mui/icons-material/MarkEmailRead';
import CreateIcon from '@mui/icons-material/Create';


function MissiveList({ chId, setMissiva, setSection }){

    const [missiveList, setMissiveList] = useState([])
    const [isLoading , setLoading]=useState(true);
    const [refresh, setRefresh] = useState(false)
    const [checked, setChecked] = useState([-1]);
    const [inbox, setInbox] = useState(true);
    var orderedMissive = [].concat(missiveList).sort((a, b) => a.receivedAt < b.receivedAt ? 1 : -1);

    useEffect(()=>{
        axios.get(API_URL.MISSIVE + '/getinbox/chId='+chId+'&inbox='+inbox, {
            headers: {
              'Authorization': 'Fervm '+getJwt()
            }})
        .then(resp=>{
            setMissiveList(resp.data.missiveList)
            //console.log("ListaMissive:", missiveList)
            setLoading(false)
        })
    },[inbox,refresh])

    if (isLoading) {
        return <div className="Loading w3-card w3-center">Loading...</div>;
    }

    const handleChecked = (event) => {
        var updatedList = [...checked];
        if (event.target.checked) {
          updatedList = [...checked, Number(event.target.value)];
        } else {
          updatedList.splice(checked.indexOf(event.target.value), 1);
        }
        setChecked(updatedList);
        //console.log(updatedList)
      };

    const openMissiva = (missiva) =>{
        if(!missiva.read){
            axios.patch(API_URL.MISSIVE + '/read', { chId: chId,idMissive:[missiva.missivaId]} , {
                headers: {
                  'Authorization': 'Fervm '+getJwt()
                }})
            .then(resp=>{
                setRefresh(!refresh)
            })
    }
        setMissiva(missiva)
        setSection('leggi')
    }

    const manageMissive = (action)=>{
          
        if(checked.length > 0 ) {
            switch(action) {
            case 'delete':
                var payload = {
                    chId: chId,
                    idMissive: checked,
                    inbox: inbox
                }
                //console.log(payload)
                axios.patch(API_URL.MISSIVE + '/delete', payload ,{
                    headers: {
                      'Authorization': 'Fervm '+getJwt()
                    }})
                .then(resp=>{        
                    if (resp.data.succes){
                        setChecked([-1])
                        setRefresh(!refresh)
                    }
                })
                break;
            case 'read' :
                var payload = {
                    chId: chId,
                    idMissive: checked
                }
               axios.patch(API_URL.MISSIVE + '/read', payload,  {
                headers: {
                  'Authorization': 'Fervm '+getJwt()
                }})
                .then(resp=>{        
                    if (resp.data.succes){
                        setChecked([-1])
                        setRefresh(!refresh)
                    }
                })
                break;
            default:
                break; 
}
        }
        
    }

    return(
        <div className="contenitori">
            <header className="d-flex" style={{justifyContent:'space-between', alignItems:'center'}}>
                <span className="d-flex"><h3>Missive {inbox ? "In Arrivo": "Spedite" }: {missiveList.length}</h3>
                <IconButton type="button" onClick={()=>{setSection('scrivi')}}>
                    <CreateIcon />
                </IconButton></span>
                <button className="ctrl-btn-M" type="button" onClick={()=>{setInbox(!inbox);}}>{inbox ? "Spedite": "In Arrivo"}</button>
            </header>
           
            <div style={{maxHeight: '80vh', overflow:'auto'}}>
           {missiveList.length > 0 && (<><Tooltip title="read">
                <IconButton  name="read" aria-label="read" type="button" onClick={()=>manageMissive("read")} >
                    <MarkEmailReadIcon />
                </IconButton>
            </Tooltip>
            <Tooltip title="delete">
                <IconButton  name="delete" aria-label="delete" type="submit" onClick={()=>manageMissive("delete")}>
                    <DeleteIcon />
                </IconButton>
            </Tooltip></>)}
            {
                orderedMissive.map((missiva, index)=>{
                   return( 
                    <div className="inbox-missiva" key={missiva.missivaId} >
                        <input
                            name="idMissive"
                            type='checkbox'
                            id={index}
                            onChange = {handleChecked}
                            checked = {checked.includes(missiva.missivaId)}
                            value={missiva.missivaId} />
                        <Missiva openFn = {openMissiva} missiva={missiva} inbox={inbox} />
                    </div> )
                })
                
            }
            
            </div>
        </div>
    )
}export default MissiveList;
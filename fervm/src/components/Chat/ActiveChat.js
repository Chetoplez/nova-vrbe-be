import React, { useEffect, useRef, useState, useContext} from 'react';
import '../Chat/ActiveChat.css';
import Tooltip from '@material-ui/core/Tooltip';
import Moment from 'moment'
import parse from 'html-react-parser';
import axios from 'axios';
import { Link} from 'react-router-dom';
import {API_URL} from '../../utils/api';
import ScrollBtn from './ScrollBtn';
import IconButton from '@material-ui/core/IconButton';
import CancelOutlinedIcon from '@material-ui/icons/CancelOutlined';
import store from 'store';
import {userContext} from '../../utils/userContext'

/**
 * Render the chat messages , I'm going brutal with polling but we are studyng the possibilty of webSocket 
 * 
 * @param {chatId} props 
 *  
 */

function ActiveChat(props) {
    const [chat, setChat] = useState([])
    const [isLoading , setLoading]=useState(true);
    const [tms, setTms] = useState(Date.now()-3600000); //an hour ago on this chat... :D
    const [fresh, setFresh] = useState(false)
    const mainContext = useContext(userContext);

   
    useEffect(()=>{
                
        setFresh(false)
        setLoading(false)
        
        axios.get(API_URL.CHAT+"/ischatupdate/chatId="+props.chatId+"&timestamp="+tms,{
            headers: {
              'Authorization': 'Fervm '+store.get('jwt')
            }})
        .then(resp=>{
            if(resp.data.updated){
                refreshChat(tms)
             }
        })

        const interval = setInterval(()=>{
            axios.get(API_URL.CHAT+"/ischatupdate/chatId="+props.chatId+"&timestamp="+tms,{
                headers: {
                  'Authorization': 'Fervm '+store.get('jwt')
                }})
            .then(resp=>{
                if(resp.data.updated){
                    refreshChat(tms)
                 }
            })
        },3000)

        return () => clearInterval(interval);
    }, [fresh])

    const refreshChat = (tms) => {
        axios.get(API_URL.CHAT+"/id="+props.chatId+"&timeWindow="+tms,{
            headers: {
              'Authorization': 'Fervm '+store.get('jwt')
            }})
        .then(resp=>{
        //    console.log(resp.data.chatMessageList)
            var newMsg = [];
            newMsg = resp.data.chatMessageList;
            setTms(newMsg[newMsg.length-1].timestamp);
            //console.log("Messaggi", newMsg)
            if(newMsg.length > 1){
                setChat(oldArray=> [...oldArray,...newMsg]);
                setLoading(false)
            }
            else{
            setChat(oldArray=> [...oldArray,resp.data.chatMessageList[newMsg.length-1]]);
        }
            setFresh(true)
          
        })
    }


    if (isLoading) {
        return <div className="Loading w3-card w3-center">Loading...</div>;
    }
    
    
    const highlightTesto = (testo)=> {
        //Parenthesis out
          var temp = testo.replace(/[{(<]/g,'[');
          temp = temp.replace(/[)>}]/g,']');  
          //Highlight text
          temp = temp.replace(/\[[^)]*?\]/g, '<span class="fervm-evidenziato">$&</span>')
          //higlight my name
          var replace = props.nomePg;
          var re = new RegExp(replace,'gi');
          temp = temp.replace(re,'<span class="fervmnome-evidenziato">'+props.nomePg+'</span>')
        return temp;

    }

    function Riga(azione) {
        //const actionRef = useRef()
        var action = highlightTesto(azione.azione.testo);

        // useEffect(()=>{
        //    if(actionRef.current !== undefined)
        //        actionRef.current.scrollIntoView({ behavior: 'smooth' });
        // })

        return(
            <div>
            <div className="info-azione">
            
                <div className="row-infopg">
                {( mainContext.roles.includes("NARRATOR") || mainContext.roles.includes("ADMIN") ) ? <CancelOutlinedIcon onClick={()=>{console.log(azione.azione.id)}} style ={{color: '#aa3232', fontSize:'14px'}}/>: null}
                    <img className="img-chat" src={azione.azione.img} alt="test"/>
                     <div style={{marginLeft:'5px'}}><Link to={"/game/profilo/"+azione.azione.characterId}><strong>{azione.azione.sender}</strong></Link></div>
                        { azione.azione.tooltip_carica === 'nessuna' ? null: <Tooltip
                        title={azione.azione.tooltip_carica}
                        placement="top">
                        <span> <img src={azione.azione.carica} alt="Carica" className="icon-carica" /></span>
                    </Tooltip>}
                    {azione.azione.tag === '' ? null : <div className="chat-tag">[{azione.azione.tag}]</div>}
                </div>
                <div >{Moment(azione.azione.timestamp).format('HH:mm')}</div>
            </div>
            <div className="testo">{parse(action)}</div>
            {/* <span ref={actionRef}></span> */}
        </div>
        )
    } 

    function RigaNarra(azione) {
        const actionRef = useRef()
        useEffect(()=>{
           if(actionRef.current !== undefined)
               actionRef.current.scrollIntoView({ behavior: 'smooth' });
        })
        return(
            <div className="riga-narra">
                <p>{azione.azione.testo}</p>
                <span ref={actionRef}></span>
            </div>
        )
    }

    return(
        <ul id="activeChat" className="contenitori">
            {chat.map((azione,index) => {       
                var rigaDavisualizzare;
                switch(azione.action){
                    case "Parla":
                      rigaDavisualizzare=<Riga azione={azione} /> 
                        break;
                        case "Dadi":
                      rigaDavisualizzare=<Riga azione={azione} /> 
                        break;
                    case "Narra" :
                        rigaDavisualizzare=<RigaNarra azione={azione} />
                        break;
                    case "Sussurra":
                        if(azione.receiver === props.nomePg | azione.sender === props.nomePg){
                            //whisper for me 
                            rigaDavisualizzare = (azione.receiver === props.nomePg) ? 
                                <div className="sussurro testo"> <span>{azione.sender} Ti sussurra: {azione.testo}</span></div> 
                                :<div className="sussurro testo"> <span>sussurri a {azione.receiver}: {azione.testo} </span></div>
                        }else {
                            rigaDavisualizzare = <div className="sussurro testo"> <span>{azione.sender} sussurra qualcosa a {azione.receiver}</span></div>
                        }
                        break;
                    default:
                        rigaDavisualizzare=<Riga azione={azione} /> 
                        break;
                }
                return (
                    <li key={index} className="chat-row">
                        {rigaDavisualizzare}
                    </li>)
            })}
            <ScrollBtn />
        </ul>
    )
}

export default ActiveChat
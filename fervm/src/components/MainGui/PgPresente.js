import React,{useContext,useState,useEffect} from 'react';
import Badge from '@material-ui/core/Badge';
import Avatar from '@material-ui/core/Avatar';
import store from 'store';
import '../MainGui/PgPresente.css';
import { withStyles,makeStyles} from '@material-ui/core'
import {Link} from 'react-router-dom'
import { userContext } from '../../utils/userContext'
import axios from 'axios';
import {API_URL} from '../../utils/api';


const StyledBadge = withStyles({
  colorPrimary: {
    backgroundColor: '#739d2c',
    color: '#fff',
    border: '2px solid'
  },
  colorError: {
    color: '#fff',
    backgroundColor: '#b73c1c',
    border: '2px solid'
  },
  dot: {
    height: '16px',
    padding: '0',
    minWidth: '16px',
    borderRadius: '8px',
    right: '30%'
  }
  
})(Badge);

const useStyles = makeStyles({
  
  dot : {
    height: '11px',
    padding: '0',
    minWidth: '11px',
    borderRadius: '6px',
    right: '25%'
  },

  bigAvatar: {
    width: 45,
    height: 45,
  },

  smallAvatar:{
    width: 35,
    height: 35,
  }
});

/**
 * 
 * @param {chatId, col} props chatId i need it when I'm on the chat component to visualize only the character in the same chat,
 * col is for have a single list or a double column list  
 * @returns 
 */
function PgPresente(props) {
  
    const mainContext = useContext(userContext)
    //const pres = (props.pres === undefined) ? [] : props.pres;
    const [pres, setPres] = useState([]);
    const classes = useStyles()
    const [fresh, setFresh] = useState(true)

    const tipoElenco = props.col === "2" ? "vrbe-badge-chat":"vrbe-badge-presente"


    useEffect(()=>{
      axios.get(API_URL.PRESENTI+"/presenti/chatId="+props.chatId,{
        headers: {
          'Authorization': 'Fervm '+store.get('jwt')
        }})
        .then(resp=>{
           setPres(resp.data.presentiChat)
           setFresh(true)
        })


      const interval = setInterval(()=>{
        axios.get(API_URL.PRESENTI+"/presenti/chatId="+props.chatId,{
          headers: {
            'Authorization': 'Fervm '+store.get('jwt')
          }})
        .then(resp=>{
           setPres(resp.data.presentiChat)
        })
    },10000) 

    return ()=>{clearInterval(interval)}

    },[fresh])


    /**
     * Change the availability status of the Character
     * 
     * @param {CharacterId clicked} idClicked 
     * @param {availability status to be modified} available 
     */
    const changeAvalability = (idClicked, available)=>{
       
        if(idClicked == mainContext.user.characterId){
          
          var payload = {
            characterId: idClicked,
            available: !available
          }
          axios.patch(API_URL.PRESENTI+"/updateavailable", payload,{
            headers: {
              'Authorization': 'Fervm '+store.get('jwt')
            }})
          .then(resp=>{
            //console.log(resp.data)
              setFresh(false);
          }).catch(err=>{
            console.log("network problem",err)
          })
        }
    }

    

    const presenti =  pres.map((user,index)=>
        <div  key={index}  className={tipoElenco}>
          
            <StyledBadge
            onClick={()=>{changeAvalability(user.characterId,user.available)}}
            color={user.available ? "primary":"error"} 
            variant = "dot"
            anchorOrigin ={{vertical: 'bottom', horizontal: 'right'}}
            overlap="circular"
            classes={{dot: props.col === "2"? classes.dot: ""}}>

              <Avatar alt={user.characterName} src={user.characterImg} className={props.col === "2" ? classes.smallAvatar:classes.bigAvatar}/>
            
            </StyledBadge>
            
          <div className={props.col === "2" ? "vrbe-small-nome-presenti":"vrbe-nome-presenti"}>
              <div><Link to={"/game/profilo/"+user.characterId}><span className='nomepg-lista'>{user.characterName}</span></Link></div>
              <div className={props.col === "2" ? "messaggio-utente" : "messaggio-utente"}>{user.messaggio}</div>
          </div>
        </div>
    );

    return(
        <div className={props.col==="2" ? "presenti-chat":"presenti-map"}>{presenti}</div>
    )
}

export default PgPresente
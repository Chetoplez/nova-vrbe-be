import React, {useContext} from 'react';
import store from 'store';
import axios from 'axios';
import PgPresente from './PgPresente';
import '../MainGui/ElencoPresenti.css'
import {useEffect,useState} from 'react'
import {API_URL} from '../../utils/api'
import { Link } from 'react-router-dom';
import { userContext } from '../../utils/userContext' 
import UserMsg from './UserMsg';

/**
 * 
 * renders the online charachater on the main map. 
 */
function ElencoPresenti(props) {
   
    const [presenti, setPresenti] = useState([]);
    const mainContext = useContext(userContext) 
    
    useEffect(()=>{
        fetchPresenti()
        const interval = setInterval(()=>{
            fetchPresenti()
         },2000)

         return () => clearInterval(interval);
    }, [])

    const fetchPresenti = ()=>{
        axios.get(API_URL.PRESENTI+"/presenti", {
            headers: {
              'Authorization': 'Fervm '+store.get('jwt')
            }})
            .then(resp=>{
               setPresenti(resp.data.presenti)                 
            }).catch(function (error) {
                  console.log(error.response.data)
                
              })
    }
    
    var presentiTotali=0 
    presenti.map((luogo)=>{
        presentiTotali+=luogo.presenteList.length;
    })
  
   return(
       <div className="lista-presenti contenitori contenitori-dx">
           <h3>{presentiTotali} Ora a FERVM</h3>
           <UserMsg id={mainContext.user.characterId} type="message" oldImg={mainContext.user.characterImg} />
           <hr className='divisore'></hr>
           {presenti.map((luogo) =>{
               return(
               <div key={luogo.idLuogo}>
                   <h4 >{luogo.presenteList.length}  {luogo.idLuogo === 0 ? luogo.nomeLuogo : <Link to={"/game/chat/"+luogo.idLuogo}>{luogo.nomeLuogo}</Link>}</h4>
                   <PgPresente col="1" chatId={luogo.idLuogo} />
               </div>
               )
           })}
       </div>
   );
}

export default ElencoPresenti
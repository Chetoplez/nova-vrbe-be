import React , {useEffect, useState, useContext} from 'react'
import {API_URL} from '../../utils/api';
import store from 'store';
import axios from 'axios';
import { Link, useNavigate } from 'react-router-dom';
import { userContext } from '../../utils/userContext';
import './GuildList.css';



/**
 * This is a temporary component. We want to upgrade as soon as we define the layout and the UI of this part
 * of the game. I need this, only to allow Guilds Navigation
 * 
 */
function GuildList () {
    const [guilds, setGuilds] = useState(null);
    const [loading, setLoading] = useState(true);
    const mainContext = useContext(userContext);
    const navigate = useNavigate()

    useEffect(()=>{
        const checkAuth = async () => {
            let res = mainContext.tryLoginUser()
            if (res) {
            } else {
                // redirect to login page 
                navigate("/", { replace: true })
            }
        }
        checkAuth();
        document.title = "Fervm GdR - Corporazioni"
        axios.get(API_URL.GUILD+"/getall",{
            headers: {
              'Authorization': 'Fervm '+store.get('jwt')
            }})
        .then(resp =>{
            setGuilds(resp.data.guilds);
            setLoading(false)
        }).catch(err=>{
            console.log("sciocco!!", err);
        })
    },[])

    if(loading){
       return <div className="loading w3-card">loading</div>;
      }

      return(
          <>
          <h1 className="w3-header w3-center">Elenco Corporazioni a Fervm</h1> 
          <table className="w3-table w3-center guildListTable">
           <tbody style={{marginTop:'100px'}}>
            {
                guilds.map((item,index)=>{
                    return(
                        <tr className='guildCard w3-card' key={index} >
                            <td className="w3-center"><Link to={""+item.id}> <h2>{item.name}</h2> </Link></td>
                        </tr>
                    )
                })
                
            }</tbody>
            </table>
          </>
      )
      
} export default GuildList;
/*
this will be the forum of the application. We need to use it to exchange comunication about the game, or the organizzation
*/
import React, {useEffect,useContext, useState} from 'react'
import axios from 'axios';
import { API_URL, getJwt } from '../../utils/api';
import { userContext } from '../../utils/userContext';
import { useLocation,Link, useParams, useNavigate } from 'react-router-dom';

function Subforum() {
    const mainContext = useContext(userContext);
    const [loading,setLoading]= useState(true);
    const [list, setList] = useState([]);
    const [forum, setForum] = useState("");
    const forumId = useParams()
    const navigate = useNavigate()
    //console.log("forumId", forumId)


    const back = ()=>{
        navigate("/game/forum")
    }

    useEffect(()=>{
        document.title = "Fervm GdR - Bacheche"
        const checkAuth = async () => {
            let res = mainContext.tryLoginUser()
            if (res) {
            } else {
                // redirect to login page 
                navigate("/", { replace: true })
            }
        }
        checkAuth();
        
        var payload = {chId: mainContext.user.characterId, forumId:forumId.idForum}
        axios.post(API_URL.SUBFORUM + "/getall", payload , {
            headers: {
            'Authorization': 'Fervm '+getJwt()
          }})
        .then(resp =>{
            var ordersubForumList = [].concat(resp.data.subForums)
                .sort((a, b) => a.visualOrder > b.visualOrder ? 1 : -1);
            setList(ordersubForumList)
            setForum(resp.data.forumName)
            setLoading(false)
        })
    },[])

    if (loading) {
        return <div className="loading">Loading...</div>;
    }

    function renderSubforumList(type){
        console.log(type)
         var test = list.find(elem=> elem.subforumType === type)
         console.log(test)
         if (test !== undefined) {
        return (
            <>            
           
            <div className='w3-half'>
            <div className='w3-header w3-center'>
               <h3>Sezione {type}</h3>
            </div>  
                    <div className='contenitori' style={{marginRight:'5px'}}>
                {
                    list.map((section, index) => {

                        if (section.subforumType === type) 
                        return (
                            <div className='' style={{ marginLeft: '30px' }} key={section.subforumId} >
                                <Link to={"posts/" + section.subforumId}><h3>{section.name} {section.unread && (<span>( Nuovi Messaggi )</span>)}</h3> </Link>
                            </div>
                        )
                    })
                }</div>
            </div>
                </>
           
        )}
        else return null;
    }

    return(
        <>
        <header style={{marginTop: '50px'}}>
            <h2 className='w3-center'>{forum}</h2>
            <div style={{display: 'flex', alignItems:'center'}}>
            
            </div>
        </header>
            <div style={{display:'flex', alignItems:'center'}}>
                <button className='ctrl-btn-M' onClick={back}>INDIETRO</button>
                {mainContext.roles.includes("ROLE_ADMIN") && (<div><Link to="create" className='ctrl-btn-M'>Crea Nuovo</Link></div>)}
            </div>
            
            <section className='d-flex w3-row'>
                {
                   renderSubforumList("ON")                
                }
                {
                    renderSubforumList("OFF")
                }
            </section> 
            
           

        </>
    )
} export default Subforum;
/*
this will be the forum of the application. We need to use it to exchange comunication about the game, or the organizzation
*/
import React, {useEffect,useContext, useState} from 'react'
import axios from 'axios';
import { API_URL, getJwt } from '../../utils/api';
import { userContext } from '../../utils/userContext';
import { useLocation,useParams,Link, useNavigate } from 'react-router-dom';
import Moment from 'moment'

function Posts() {
    const mainContext = useContext(userContext);
    const [loading,setLoading]= useState(true);
    const [subforum, setSubforum] = useState({});
    const [list, setList] = useState([]);
    let { subforumId } = useParams(); 
    const location  = useLocation();
    const navigate = useNavigate()
    
    useEffect(()=>{
        document.title = "Fervm GdR - Posts"

        const checkAuth = async () => {
            let res = mainContext.tryLoginUser()
            if (res) {
            } else {
                // redirect to login page 
                navigate("/", { replace: true })
            }
        }
        checkAuth();

        axios.get(API_URL.SUBFORUM + "/getDetail/"+subforumId,{
            headers: {
            'Authorization': 'Fervm '+getJwt()
          }})
        .then(resp=>{
            //console.log(resp.data)
            setSubforum(resp.data.subForum)
        })


        axios.get(API_URL.POST + "/getall/"+subforumId,{
            headers: {
            'Authorization': 'Fervm '+getJwt()
          }})
        .then(resp =>{
            //console.log("cacchio",resp.data.postList)
            setList(resp.data.postList)
            setLoading(false)
        })


    },[])

    if (loading) {
        return <div className="loading">Loading...</div>;
    }

    const back = ()=>{
       navigate("/game/forum/subforums/"+subforum.forumId)
    }

    return(
        <>
            <header className='w3-center'>
                <h1>{subforum.name}</h1>
            </header>
            <div style={{display:'flex', alignItems:'center'}}>
                <button className='ctrl-btn-M' onClick={back}>INDIETRO</button>
                <div><Link to="create" className='ctrl-btn-M'>Crea Nuovo</Link></div>
            </div>
            <table className='contenitori' style={{width:'100%', margin:'auto'}}>
                <tbody>
                    {
                        list.map((post,index)=>{
                            return(
                                <tr className='postCard w3-card' key={post.postId} >
                                    <td> <Link to={"/game/forum/post/detail/"+post.postId}><h3>{post.title}</h3></Link></td>
                                    <td style={{lineHeight: '16px'}}>
                                        <span> <strong style={{color:'#554641'}}> Autore: {post.author.characterName +' '+post.author.characterSurname}</strong></span><br></br>
                                        <span> <strong style={{color:'#554641'}}> Creato: {Moment(post.createdAt).format('DD/MM/YYYY - HH:mm')}</strong></span><br></br>
                                        <span> <strong style={{color:'#554641'}}>Ultima Modifica: {Moment(post.lastModified).format('DD/MM/YYYY - HH:mm')}</strong></span><br></br>
                                        <span> <strong style={{color:'#554641'}}> Stato: {post.closed ? <span style={{color:'red'}}>Chiuso</span>: <span style={{color:'green'}}>Aperto</span>}</strong></span>
                                    </td>
                                </tr>
                            )
                        })
                        
                    }
                </tbody>
            </table>

        </>
    )
} export default Posts;
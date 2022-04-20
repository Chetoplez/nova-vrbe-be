import axios from 'axios';
import React, {useEffect,useContext, useState} from 'react'

import { useParams, useNavigate } from 'react-router-dom';
import { API_URL, getJwt, sanitazeHTML } from '../../utils/api';
import { userContext } from '../../utils/userContext';
import './Post.css'
import ConversationList from './ConversationList';
import Author from './Author';
import parse from 'html-react-parser';

function Post() {
    const [post, setPost] = useState({});
    const [loading,setLoading]= useState(true);
    const [newComment, setNewComment] = useState(false);
    const postId = useParams()
    const [comment, setComment] = useState("")
    const mainContext = useContext(userContext);
    const navigate = useNavigate();
    const [inEdit, setEdit] = useState(false);
    const [body, setBody] = useState("")

    useEffect(()=>{
        const checkAuth = async () => {
            let res = mainContext.tryLoginUser()
            if (!res) {
                // redirect to login page 
                navigate("/", { replace: true })
            }
        }
        
        axios.get(API_URL.POST+"/postdetail/"+postId.postId, {
            headers: {
            'Authorization': 'Fervm '+getJwt()
          }})
        .then(resp=>{
            console.log(resp.data)
            setPost(resp.data.detail);
            setBody(resp.data.detail.body)
            setLoading(false)
        })

        checkAuth();
    },[comment])

    if (loading) {
        return <div className="App">Loading...</div>;
    }

    const back = ()=>{
        navigate('/game/forum/subforums/'+post.forumId+'/posts/'+post.subforumId)
    }

    const handleSubmit = (evt)=>{
        evt.preventDefault()
        var payload = {
            chId: mainContext.user.characterId,
            postId: post.postId,
            comment: {
                body: sanitazeHTML(comment),
                author : {
                    characterId: mainContext.user.characterId,
                },
                postId: post.postId,
                createdAt: Date.now()
            }
        }
       
        axios.put(API_URL.COMMENT+"/create", payload, {
            headers: {
            'Authorization': 'Fervm '+getJwt()
          }})
        .then(resp=>{
            setNewComment(true)
            setComment("")
            console.log(resp.data)
        
        })
    }

    const  HandleTextChange = (evt)=>{
        setComment(evt.target.value);
    }

    const updatePost = ()=>{
        const newPost = {...post }
        if(inEdit){
            newPost.body = sanitazeHTML(body);
            newPost.lastModified = Date.now();
            var payload ={
                chId: mainContext.user.characterId,
                postId : post.postId,
                editedPost: newPost
            }
            console.log("PayloadEdit:",payload)
            axios.patch(API_URL.POST+"/edit", payload, {
                headers: {
                    Authorization: 'Fervm '+getJwt()
                }
            }).then(resp=>{
                setPost(newPost);
                setBody(newPost.body)
            })
        }
        setEdit(!inEdit);
    }

    const aggiornaPost = (evt)=>{
       setBody(evt.target.value);
    }

    

    return(
        <div style={{overflow:'auto'}}>
            <button className='ctrl-btn-M' onClick={back}>INDIETRO</button>
            <section className='postSection'>
                <div style={{borderRight: '1px solid black'}}>
                    <Author post={post}/>
                    {(mainContext.user.characterId == post.author.characterId || mainContext.roles.includes("ROLE_ADMIN")) && 
                    <button type='button' onClick={updatePost} className='ctrl-btn-M'>{inEdit ? 'Salva' : 'Modifica'}</button>}
                </div>
                <div className='postBody'>
                    <header>
                        <h2 className='w3-center'>{post.title}</h2>
                    </header>
                    {!inEdit ? <div className='postTesto'>{parse(sanitazeHTML(post.body))}</div> 
                        : <div>
                            <textarea style={{height:'350px', resize:'none'}} className='postTesto' value={body} onChange ={aggiornaPost} ></textarea>
                            
                        </div>}
                </div>
                
            </section>
            { !post.closed && (<ConversationList newComment={newComment} postId = {post.postId}/>) }
            { !post.closed && (
                <form className='postSection' onSubmit={handleSubmit }>
                    <textarea style={{width:'100%'}} value={comment} onChange={HandleTextChange} ></textarea>
                    <div style={{float:'left'}}>
                        <button value="rispondi" type='submit' className='ctrl-btn-M'>Rispondi</button> 
                    </div>
                </form>)}
            
        </div>
    )
} export default Post;
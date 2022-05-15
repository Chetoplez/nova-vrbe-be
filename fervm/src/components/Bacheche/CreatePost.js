import React, { useEffect, useContext } from 'react'
import { TextField } from "@material-ui/core";
import { useForm } from "react-hook-form";
import axios from 'axios';
import { API_URL, getJwt,sanitazeHTML } from '../../utils/api';
import { userContext } from '../../utils/userContext';
import { useNavigate, useParams } from 'react-router-dom';
import './CreatePost.css'

function CreatePost(){   
    const { register, handleSubmit,  formState: { errors } } = useForm({mode: 'all'});
    const mainContext = useContext(userContext);
    const navigate = useNavigate();
    const param = useParams()

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
    },[])

    const submitForm = (values) =>{
        var payload = {
          chId: mainContext.user.characterId,
          post: {
            title: sanitazeHTML(values.title),
            body: sanitazeHTML(values.body),
            forumId: param.idForum,
            subforumId: param.subforumId,
            author: {characterId: mainContext.user.characterId},
            isPinned: false,
            isClosed: false,
            createdAt: Date.now()
          }
        }
       //console.log("payload", payload)
        axios.put(API_URL.POST+"/create", payload,{
         headers: {
           'Authorization': 'Fervm '+getJwt()
         }})
        .then(resp => {
           navigate("/game/forum/post/detail/"+resp.data.postId )
        }).catch(err=>{
          console.log("CreateSubForum",err);
        })
   
      } 


    return(
        <>
        <header className='w3-center'>
            <h1>Nuovo Post</h1>
        </header>
        <form onSubmit={handleSubmit(submitForm)} className="w3-card w3-padding w3-container contenitori" style={{width:'50%', margin:'25px auto'}}>
            <h3>Titolo</h3>
            <TextField style={{minWidth:"300px"}} placeholder="Titolo"
                name="title"
                {...register("title", { required: true, minLength: 3, maxLength: 25 })} /><br></br>
                <p style={{ fontSize: '13px', color: "red" }}>{errors.title?.type === 'required' && "Questo campo è obbligatorio"}</p>
            <textarea  type='textarea'
                    aria-multiline
                    placeholder='Scrivi il Post'
                    name='body' 
                    className='postTextArea'
                    {...register("body", { required: true, minLength: 1 })}>
                    
             </textarea>
             <p style={{ fontSize: '13px', color: "red" }}>{errors.body?.type === 'required' && "Questo campo è obbligatorio"}</p>
            <p>
                <button type='button' className='primary-btn-M' onClick={()=>{navigate("/game/forum/subforums/"+param.idForum+"/posts/"+param.subforumId)}}>Indietro</button>
                <button type='submit' className='primary-btn-M'>Crea</button>
            </p>
            
        </form>
        </>
    )
} export default CreatePost;

import axios from "axios";
import React, {useEffect, useState,useContext} from "react";
import Author from "./Author";
import { API_URL, getJwt, sanitazeHTML } from "../../utils/api";
import { userContext } from '../../utils/userContext';
import parse from 'html-react-parser';
import './CommentList.css'


function CommentList({ comment }) {
    const [commentList, setCommentList] = useState([]);
    const [testo, setTesto] = useState("");
    const [hide, setHide] = useState(true)
    const [fresh, setFresh] = useState(true);
    const mainContext = useContext(userContext);
    
    useEffect(()=>{
        
        axios.get(API_URL.COMMENT+"/getrelated/"+comment.commentId, 
        {headers: {
          'Authorization': 'Fervm '+getJwt()
        }})
        .then(resp=>{
            //console.log("ListaCommentiRelated", resp.data.commentList);
            setCommentList(resp.data.commentList)
        })
    },[fresh, comment.commentId])


    const handleSubmit = (evt)=>{
        evt.preventDefault()
        var payload = {
            chId: mainContext.user.characterId,
            postId: comment.postId,
            comment: {
                body: sanitazeHTML(testo),
                author : {
                    characterId: mainContext.user.characterId,
                },
                postId: comment.postId,
                relatedComment: comment.commentId,
                createdAt: Date.now()
            }
        }
        //console.log(payload)
        axios.put(API_URL.COMMENT+"/create", payload,
        {
            headers: {
            'Authorization': 'Fervm '+getJwt()
          }})
        .then(resp=>{
            //console.log(resp.data)
            setFresh(false)
            setTesto("");
        })
    }

    const  HandleTextChange = (evt)=>{
        setTesto(evt.target.value);
    }

    const close = ()=>{
        setHide(!hide)
    }

    return(
        <div style={{margin:'auto' , width: '70%'}}>
            <div style={{display: 'flex', alignItems: 'center'}}><h4> Commenti : {commentList.length}</h4> <h4 style={{cursor: 'pointer', marginLeft: '20px'}} onClick={close}>{hide ? 'Apri': 'Nascondi'}</h4></div>
            <div style={{display: hide ? 'none': 'block'}}>
                {commentList.map((risposta)=>{
                    return(
                        <div key={risposta.commentId} 
                         className="postSection">
                            <Author post={risposta}></Author>
                            <div className="respTesto">{parse(sanitazeHTML(risposta.body))}</div>
                        </div>
                    )
                })}
                <form className="postSection" onSubmit={handleSubmit}>
                    <textarea style={{width:'100%', borderRadius:'5px'}} value={testo} onChange={HandleTextChange} ></textarea>
                    <div style={{float:'right'}}>
                            <button value="commenta" type='submit' className='ctrl-btn-M'>Commenta</button>
                    </div>
                </form>
            </div>
        </div>
    )
}export default CommentList;
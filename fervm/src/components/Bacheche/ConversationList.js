
import axios from "axios";
import React, {useEffect, useState} from "react";
import Author from "./Author";
import store from "store";
import { API_URL, getJwt, sanitazeHTML } from "../../utils/api";
import CommentList from "./CommentList";
import parse from 'html-react-parser';
import './ConversationList.css';

function ConversationList(props) {
    const [commentList, setCommentList] = useState([]);

    useEffect(()=>{
        axios.get(API_URL.COMMENT+"/getpostcomment/"+props.postId, {
            headers: {
            'Authorization': 'Fervm '+getJwt()
          }})
        .then(resp=>{
            //console.log("ListaCommenti", resp.data.commentList);
            setCommentList(resp.data.commentList)
        })
    },[props.newComment])

    return(
        
        <div >
            <p>Ci sono {commentList.length} risposte</p>
            {commentList.map((risposta)=>{
                return(
                 <div key={risposta.commentId} style={{display:'flow-root'}}>
                 
                    <div  className ="postSection">
                        <Author post={risposta}></Author>
                        <section className="postBody">
                            <div className="postTesto">{parse(sanitazeHTML(risposta.body))}</div>
                        </section>
                    </div>
                    <CommentList comment={risposta} /> 
                </div>
                )
            })}
        </div>
    )
}export default ConversationList;
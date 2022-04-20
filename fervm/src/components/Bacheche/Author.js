import React from "react";
import Moment from "moment";
import Avatar from '@material-ui/core/Avatar';
import './Author.css'

function Author({ post }){
    
    return(
        <section  >
            <div className="author">
                <Avatar src={post.author.characterImg}></Avatar> 
                <div>
                    <div>Autore: {post.author.characterName}</div>
                    <div>Creato: {Moment(post.createdAt).format('DD/MM/YYYY - HH:mm')}</div>
                    {post.lastModified !== null && <div>Ultima Modifica: {Moment(post.lastModified).format('DD/MM/YYYY - HH:mm')}</div>}
                </div>
            </div>
            {post.closed && (<p style={{color: 'red', textAlign:'center'}}>Post chiuso. Risposte disabilitate</p>)}
        </section>
    )
} export default Author;
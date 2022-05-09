import React from "react";
import { Tooltip } from '@material-ui/core';
import { Link } from 'react-router-dom'
import '../MainGui/MainMap.css';

function ChatDot({ chat, heigt, width }) {
    
    return(
        <Link to={"/game/chat/"+chat.idChat}>
            <Tooltip title={chat.nomeChat}>
                <span className="dot" style={{top:(heigt/100) * chat.pos_y , left:(width/100) * chat.pos_x }}></span>
            </Tooltip>
        </Link>
    )
} export default ChatDot;
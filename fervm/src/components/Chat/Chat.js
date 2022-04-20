import React, { useState, useEffect, useContext } from 'react'
import { useParams,useNavigate } from 'react-router-dom'

import ActiveChat from './ActiveChat.js'

import ControlliChat from './ControlliChat.js'
import PgPresente from '../MainGui/PgPresente'
import '../Chat/Chat.css';
import store from 'store'
import { API_URL } from '../../utils/api'
import axios from 'axios';
import { userContext } from '../../utils/userContext'

/**
 * It render the chat page. We have the active chat component that renders the messages, the controlliChat component that allow to 
 * write and send actions and messages and the list of characters in the same chat
 * 
 * @param {nomePg} props 
 * @returns 
 */

function Chat(props) {
    let { id } = useParams();
    const navigate = useNavigate()

    const [isLoading, setLoading] = useState(true);
    const mainContext = useContext(userContext);


    if (id === undefined)
        id = store.get('idUrl')
    else store.set('idUrl', id);
    props.setIdLuogo(id)

    useEffect(() => {
        const checkAuth = async () => {
            let res = mainContext.tryLoginUser()
            if (res) { 
                
            } else {
                // redirect to login page non aha 
                navigate("/")
            }
        }
        checkAuth();
        document.title = 'Fervm GdR - Chat'
        var payload = {
            characterId: mainContext.user.characterId,
            idLuogo: id
        }
        axios.patch(API_URL.PRESENTI + "/moveto", payload, {
            headers: {
                'Authorization': 'Fervm ' + store.get('jwt')
            }
        })
            .then(test => {
                setLoading(false)
            })

        return () => {
            axios.patch(API_URL.PRESENTI + "/moveto", {
                characterId: mainContext.user.characterId,
                idLuogo: 0
            }, {
                headers: {
                    'Authorization': 'Fervm ' + store.get('jwt')
                }
            });
            store.remove('idUrl');
        }
    }, [id])




    if (isLoading) {
        return <div className="App">Loading...</div>;
    }

    return (
        <div className="chat-container ">
            <div className='colonna-sx'>
                <ActiveChat nomePg={props.nomePg} chatId={id} />
            </div>

            <div className='colonna-dx'>
                <div className="contenitori">
                    <ControlliChat chatId={id} />
                </div>
                <div className="contenitori">
                    <PgPresente col="2" chatId={id} />
                </div>
            </div>
        </div>
    );

}

export default Chat
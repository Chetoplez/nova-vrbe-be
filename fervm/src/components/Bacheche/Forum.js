/*
this will be the forum of the application. We need to use it to exchange comunication about the game, or the organizzation
*/
import React, { useEffect, useContext, useState } from 'react'
import axios from 'axios';
import store from 'store';
import { API_URL, getJwt } from '../../utils/api';
import { userContext } from '../../utils/userContext';
import { Link, useNavigate } from 'react-router-dom';
import ForumCard from './ForumCard';
import './Forum.css';



function Forum() {
    const mainContext = useContext(userContext);
    const [loading, setLoading] = useState(true);
    const [forumList, setForumList] = useState([]);
    const navigate = useNavigate()

    useEffect(() => {
        document.title = "Fervm GdR - Bacheche";

        const checkAuth = async () => {
            let res = mainContext.tryLoginUser()
            if (res) {
            } else {
                // redirect to login page 
                navigate("/", { replace: true })
            }
        }
        checkAuth();

        axios.get(API_URL.FORUM + "/getallforums/chId=" + mainContext.user.characterId, {
            headers: {
                'Authorization': 'Fervm ' + getJwt()
            }
        })
            .then(resp => {
                var orderForumList = [].concat(resp.data.forumsList)
                .sort((a, b) => a.visualOrder > b.visualOrder ? 1 : -1);
                setForumList(orderForumList)
                setLoading(false)
            })
    }, [])

    if (loading) {
        return <div className="loading">Loading...</div>;
    }

    function renderForumList(type) {
        
        return (       
                <>
                {
                    forumList.map((forum) => {
                        if (forum.forumType === type && forum.ownedBy == -1)
                            return (
                                <ForumCard key={forum.forumId} forum={forum} guild={false} />
                            )
                    })

                }
                </>
           
        )
    }

    function renderGuildForumList() {
        return (       
                <>
                {
                    forumList.map((forum) => {
                        if (forum.ownedBy != -1)
                            return (
                                <ForumCard forum={forum} guild={true} />
                            )
                    })

                }
                </>
           
        )
    }

    return (
        <div className="chat-container">
            <section className='colonna-sx'>
                <div className='contenitori'>
                    <div className='d-flex flex-wrap'>
                        <div className='flex-basis-1 w3-half'>
                            <div className='w3-header w3-center'>
                                <h2>Vita Pubblica</h2>
                            </div>
                            {renderForumList("ON")}
                        </div>
                        <div className='flex-basis-1 w3-half'>
                            <div className='w3-header w3-center'>
                                <h2>Vita di Corporazione</h2>
                            </div>
                            {renderGuildForumList()}
                        </div>
                    </div>
                </div>
                <p>{mainContext.roles.includes("ROLE_ADMIN") && (<div><Link to="create" className='ctrl-btn-M'>Crea Nuovo</Link></div>)}</p>
            </section>
            <section className='colonna-dx'>
                <div className='contenitori'>
                    <header className='w3-center'>
                        <h2>OFF</h2>
                    </header>
                    {renderForumList("OFF")}
                </div>
            </section>

        </div>
    )
} export default Forum;
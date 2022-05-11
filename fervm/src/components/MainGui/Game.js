import React, { useContext, useState, useEffect } from 'react'

import Nav from '../Navbar/Nav';
import Chat from '../Chat/Chat';
import MainMap from '../MainGui/MainMap';
import ProfiloPg from '../Character/ProfiloPg';
import { Routes, useNavigate, Route } from 'react-router-dom';

import { userContext } from '../../utils/userContext'
import GuildPage from '../Guild/GuildPage';
import GuildList from '../Guild/GuildList';

import Forum from '../Bacheche/Forum';
import Posts from '../Bacheche/Posts';
import Post from '../Bacheche/Post';
import Subforum from '../Bacheche/Subforum';
import CreateForum from '../Bacheche/CreateForum';
import CreateSubforum from '../Bacheche/CreateSubforum'; 
import CreatePost from '../Bacheche/CreatePost';
import GuildBank from '../Guild/GuildBank';
import PrivateGuildRoute from '../../utils/PrivateGuildRoute';
import MissivaPage from '../Missive/MissivePage';

/**
 * 
 * @returns the main board of the game. The Route is "private" so you can access only after the login. 
 */
function Game() {

    const history = useNavigate()
    const [loading, setLoading] = useState(true);
    const [idLuogo, setIdLuogo] = useState('0') //il default è "in giro per la città"
    const mainContext = useContext(userContext);
    document.title = 'Fervm GdR - Game'

    var body = document.getElementById("body")
    body.classList.remove("home-img")

    useEffect(() => {
          const checkAuth = async () => {
            let res = mainContext.tryLoginUser()
            if (res) { 
                setLoading(false);
            } else {
                // redirect to login page non aha 
                history("/")
            }
        }
        checkAuth();

    }, [])

    if (loading) {
        return <div className="loading w3-card">Loading</div>
    }

    return (
        <>
            <Nav idLuogo={idLuogo} />
            <div className="under-nav">
                <Routes>
                    <Route exact path="/chat">
                        <Route path=":id" element={<Chat setIdLuogo={setIdLuogo}
                            nomePg={mainContext.user.characterName}
                            idPg={mainContext.user.characterId} />} >
                        </Route>
                    </Route>

                    <Route
                        path='/mainmap'
                        element={<MainMap setIdLuogo={setIdLuogo} />}>

                    </Route>

                    <Route exact path='/profilo'>
                        <Route path=":idPg" element={<ProfiloPg></ProfiloPg>}></Route>
                    </Route>

                    <Route
                        exact path="/corporazioni"
                        element={<GuildList />}></Route> 

                    <Route  path="/corporazioni">
                        <Route exact path=":idGilda" element={<GuildPage />}></Route>
                    </Route>

                    <Route  path="/corporazioni/:idGilda/banca"
                        element={
                            <PrivateGuildRoute>
                                <GuildBank />
                            </PrivateGuildRoute>}>
                    </Route>
                  
                    <Route
                        exact path="/missive"
                        element={<MissivaPage />}></Route>

                    <Route
                        exact path="/forum"
                        element={<Forum />}>
                    </Route>
                    <Route
                        exact path="/forum/create"
                        element={<CreateForum />}>
                    </Route>
                    
                    <Route exact path='/forum/subforums'>
                            <Route exact path=':idForum' element = {<Subforum ></Subforum>}></Route>
                    </Route>

                    <Route
                        exact path="/forum/subforums/:idForum/create"
                        element={<CreateSubforum />}>
                    </Route>

                     <Route
                        exatct path ="/forum/subforums/:idForum/posts">
                        <Route exact path =":subforumId" element={<Posts />}></Route>                       
                    </Route>

                    <Route
                        exatct path ="/forum/subforums/:idForum/posts/:subforumId/create"
                        element={<CreatePost />}>
                    </Route>

                    <Route
                        path ="/forum/post/detail">
                        <Route exact path =":postId" element={<Post />}></Route>                       
                    </Route>
                </Routes>
            </div>


        </>
    )

}

export default Game
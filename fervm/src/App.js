import './App.css';
import React, { useEffect, useContext } from 'react';
import { BrowserRouter,  Route, Routes} from 'react-router-dom';
import MainContextProvider from './utils/userContext'
import UserMessageNotifcation from './utils/UserMessageNotification';
import UserMessageProvider from './utils/messageContext';
import Game from './components/MainGui/Game';
import Home from './components/Home';
import About from './components/About'
import Regolamento from './components/Regolamento'
import PrivateRoute from './utils/PrivateRoute';
import Subscribe from './components/Registrazione/Subscribe';
import CharacterCreation from './components/Registrazione/CharacterCreation';
import axios from 'axios';
import {API_URL, getJwt} from './utils/api'
import { userContext } from './utils/userContext'




function App() {

const mainContext = useContext(userContext);
useEffect(() => {
  const handleTabClose = event => {
    event.preventDefault();

    axios.post(API_URL.USER+"/logout",
    {chId: mainContext.user.characterId}).then(resp=>{
        if(resp.data.success)
            mainContext.tryLogout()
    })

    return (true);
  };

  window.addEventListener('beforeunload', handleTabClose);

  // return () => {
  //   window.removeEventListener('beforeunload', handleTabClose);
  // };
}, []);
  
  
    
  return ( 
  <BrowserRouter>
    <MainContextProvider>
    <UserMessageProvider>
    <UserMessageNotifcation />
    <div className="App">
    
        <Routes>
              <Route exact path="/" element={<Home />} />

              <Route exact path="/about" element={<About/>} />

              <Route exact path="/regolamento" element={<Regolamento />} />

              <Route exact path="/subscribe" element={<Subscribe />} />

              <Route exact path="/subscribe/newcharacter">
                <Route exact path =":newId" element={<CharacterCreation />} ></Route>
              </Route>
            
              <Route exact path="/game/*" 
                element={
                  <PrivateRoute>
                    <Game />
                  </PrivateRoute>} 
                />
        </Routes>
    </div>
    </UserMessageProvider>
    </MainContextProvider>
    </BrowserRouter>
  );
}

export default App;

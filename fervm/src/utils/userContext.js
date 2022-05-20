import React, { useState, useCallback } from 'react'
import { useNavigate } from 'react-router-dom'
import axios from 'axios';

import store from 'store'
import { API_URL, getJwt } from './api';

const userContext = React.createContext({
  user: {},
  setUser: () => { },
  isLoggedIn: () => { return false }
}); // Create a context object

export {
  userContext // Export it so it can be used by other Components
};


const MainContextProvider = ({ children }) => {
  const [user, setUser] = useState(store.get('user'));
  const history = useNavigate()
  const [isLoggedIn, setLoggedIn] = useState(false);
  const [roles, setRoles] = useState(store.get('roles'))

  const tryLogout = useCallback(async () => {
    setLoggedIn(false);
    store.set('isLogin', false)
    store.remove('user')
    store.remove('jwt')
    store.clearAll();
    history('/');

  }, []);

  const tryLoginUser = useCallback(async () => {
    let isLog = false;
    //console.log("tryLogin: IsLoggedIn:", isLoggedIn)
    if (isLoggedIn === false) {
      // check local data
      var userlocal = store.get('user');
      var localRoles = store.get('roles');

      if (userlocal === undefined) {
        //nothing on the local storage
        isLog = false
      } else {
        setUser(userlocal);
        setRoles(localRoles);
        isLog = true
      }
      // await
      //TODO fetch server - is token valid? - I am starting to play
      axios.patch(API_URL.PRESENTI + "/getonline", { characterId: user.characterId, online: isLog }, {
            headers: {
              'Authorization': 'Fervm ' + getJwt()
            }
          }).catch(function (error) {
            if (error.response && error.response.status === 417) {
              store.clearAll()
              setLoggedIn(false)
              history("/");
            }
          });

      setLoggedIn(isLog);
    } else { isLog = true; }

    return isLog;
  }, [isLoggedIn, setLoggedIn])

  return (
    <userContext.Provider value={{ user, setUser, isLoggedIn, setLoggedIn, tryLoginUser, tryLogout, roles, setRoles }}>
      {children}
    </userContext.Provider>
  )
}

export default MainContextProvider;
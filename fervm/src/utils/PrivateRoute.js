
import React,{useContext} from 'react';
import { Navigate, useNavigate } from 'react-router-dom';
import { userContext } from '../utils/userContext'
import store from 'store'
import { API_URL, getJwt } from './api';
import axios from 'axios';
import useMessage from './useMessage';

/**
 * It send you on the home page if you clicked the logout button or, if you are not logged in and try to visit a protected route on game.
 * @param {*} param0 
 * @returns 
 */
const PrivateRoute = ({ children }) => {
    const mainContext = useContext(userContext);
    const navigate = useNavigate()
    const { addMessage } = useMessage();

    async function isTokenValid(){
    let jwt = getJwt()
    
    let valid = false;
    if (jwt !== null ) {
     valid = await checkToken()
    }else
    {
        navigate("/")
    }
    
    return valid;
 }

 function checkToken(){
    
    return axios.get(API_URL.JWT, {
        headers: {
          'Authorization': 'Fervm '+getJwt()
        }})
    .then((resp)=>{ 
        
        var valid = resp.data;
        return valid;
    }).catch(err=>{
       
        if(err.response.status === 417) {
            addMessage("la tua sessione è scaduta! Devi effettuare il login")
            navigate("/");
    }
    })
 }

    if(!isTokenValid() && mainContext.isLoggedIn){
        
        return <Navigate to={"/"} />
    }

    return children
    
};

export default PrivateRoute;

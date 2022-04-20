
import React,{useContext} from 'react';
import { Navigate, useLocation } from 'react-router-dom';
import { userContext } from '../utils/userContext'
import store from 'store'

/**
 * It send you on the home page if you clicked the logout button or, if you are not logged in and try to visit a protected route on game.
 * @param {*} param0 
 * @returns 
 */
const PrivateRoute = ({ children }) => {
    const mainContext = useContext(userContext);
    let location = useLocation();
    if(!mainContext.isLoggedIn && !store.get('isLogin')){
        console.log("PrivateRoute!", mainContext.isLoggedIn)
        return <Navigate to="/" state={{from : location}} ></Navigate>
    }

    return children
    
};

export default PrivateRoute;

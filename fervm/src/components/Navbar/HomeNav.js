import React from 'react'
import './HomeNav.css'
import { Link} from 'react-router-dom';

/**
 * 
 * @returns the home page navigation bar
 */
function HomeNav () {
    return(
        <div className="w3-row container-home homenav">
        <h1 style={{width:'50%'}} className="w3-header game-title"></h1>
        <div className="w3-half rightSide">
          <div className="w3-bar-item"><Link to="/">Home</Link></div>
          <div className="w3-bar-item"><Link to="/regolamento">Regolamento</Link></div>
          <div className="w3-bar-item"><Link to="/about">Su di Noi</Link></div>
        </div>
        </div>
    )
} export default HomeNav;
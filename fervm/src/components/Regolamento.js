import React from 'react'
import HomeNav from './Navbar/HomeNav';
function Regolamento() {
    
    document.title = 'Fervm GdR - Regolamento'
    var body = document.getElementById("body")
    body.classList.remove("home-img")
    
    return(
        <>
        <HomeNav />
        <div className="under-nav">
            <h1>Regolamento</h1>
        </div>
        </>
    )
} export default Regolamento;
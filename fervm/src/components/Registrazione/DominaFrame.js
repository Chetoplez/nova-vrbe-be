import React from "react";
import dominus from './img/domina.png'

function DominaFrame() {

    return(
        <div>
            <div className="w3-center">
                <img style={{width:'80%', textAlign:'center'}} src={dominus} />
            </div>
            <div className="frame-desc">
             <p>Nella Repubblica romana, le domine <strong>amministrano</strong> spesso potere e cariche,
            ma da dietro le quinte dato che la società romana è patriarcale.</p>

            <p>Non disperare, Fervm fa eccezione: qui le donne possono ricoprire la <strong>maggior
             parte dei ruoli</strong>, ad eccezione della Legio.
             <br />Se saprai utilizzare le abilità di cui gli dei ti hanno fatto dono, Fervum sarà per te il 
             luogo ideale dove raggiungere qualsiasi <strong>obiettivo</strong> tu ti sia prefissata…</p> 
            </div>
        </div>
    )
}export default DominaFrame;
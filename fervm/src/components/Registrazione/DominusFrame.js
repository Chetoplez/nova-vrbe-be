import React from "react";
import dominus from './img/dominus.png'

function DominusFrame() {

    return(
        <div>
            <div className="w3-center">
                <img style={{width:'80%', textAlign:'center'}} src={dominus} />
            </div>
            <div className="frame-desc">
                <p>La società nell’antica Repubblica Romana è <strong>patriarcale</strong>.
                L’uomo può ricoprire qualsiasi ruolo voglia, ovviamente a seconda della casta di appartenenza: 
                è fondamentale essere un cittadino romano.</p>

                <p>In Fervm potrai farti strada, guadagnarti la tua cittadinanza e arrivare presto a ricoprire ruoli di rilievo, 
                oppure creare i tuoi loschi giri nelle peggiori zone della città: tutto dipenderà da te,
                dai tuoi obiettivi, e da quanto saprai destreggiarti bene… </p>
            </div>
        </div>
    )
}export default DominusFrame;
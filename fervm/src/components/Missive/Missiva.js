import React from 'react'
import Moment from 'moment'

function Missiva({ openFn , missiva }){

    
    return(
        <>
            <div style={{borderBottom:'2px solid', marginBottom:'5px', width:'100%'}}>

                <section style={{display:'flex', alignItems:'center'}}>
                
                <div style={{width:'90%', margin:'auto'}} onClick={()=>{openFn(missiva)}}>
                    <div style={{display:'flex', justifyContent:'space-between'}}>
                        <div>Da: {missiva.from.characterName +' '+missiva.from.characterSurname}</div> 
                        <div>{Moment(missiva.receivedAt).format('DD/MM/YYYY HH:mm')}</div>
                    </div>
                    <div>Oggetto: {missiva.subject}</div>
                    <div>Tipo: {missiva.type}</div> 
                    <div>Stato: {missiva.read ? (<span>Letta</span>) : (<strong style={{color:'burlywood'}}>Da Leggere</strong>)}</div>
                </div>

                   
                </section>
            </div>
        </>
    )
} export default Missiva
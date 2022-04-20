import {React, useContext } from 'react'
import Moment from 'moment'
import { userContext } from '../../utils/userContext'
import './ItemDetails.css'

function ItemDetails(props) {

    const mainContext = useContext(userContext)
    const item = props.item
    console.log(item)    

    return(
        <div className='detailContainer w3-card'>
            <h2  style={{textTransform: 'uppercase', textAlign:'center'}}>{item.name}</h2>
            <div className="detailImgContainer">
              <img src={item.url} alt={item.name} className="detailImg"></img>
            </div>
            <div>{item.modifiers.map((car,index)=>{
                return(
                    <div className="modifiers" key={index}>
                        <span>
                            <strong>{car.stat}</strong>:</span>
                            <span style={{color: car.modifier > 0 ? '#0BA400': '#A40000', marginLeft:'10px', fontWeight:'600'}}>{car.modifier}</span>
                        {car.isTemporary ? <div>
                        <div>RISOLVE: {car.healthStatus}</div>
                        <div>Durata Effetto: {car.duration}</div>
                        </div> : null}
                    </div>
                )
            })}
            </div>
            <div>
              <div className="modifiers"><strong>ACQUISTATO:</strong> {Moment(item.acquiringDate).format('YYYY/MM/DD')}</div>
              <div className="modifiers"><strong>SCADENZA:</strong> {Moment(item.acquiringDate).add(item.duration,'day').format('YYYY/MM/DD')}</div>
            </div>
            <div className="descr-oggetto">
                {item.description}
            </div>
            <div className="passa-oggetto">
                { (props.owner.characterId ===mainContext.user.characterId) && <button className='main-btn-M'>Invia</button>}
            </div>
        </div>
    )
}
export default ItemDetails
import axios from 'axios'
import { useEffect, useState } from 'react'
import { DndProvider } from 'react-dnd'
import { HTML5Backend } from 'react-dnd-html5-backend'
import { API_URL, getJwt } from '../../utils/api'
import CharacterInventoryEquipped from './CharacterInventoryEquipped'
import CharacterInventoryBag from './CharacterInventoryBag'
import ItemDetails from './ItemDetails'

import './CharacterInventory.css'

const CharacterInventory = ({ character }) => {
    const [detail, setDetail] = useState(null)
    const [inventory, setInventory] = useState([])
    const [isloading, setLoading] = useState(true)
    const [sesterzi, setSesterzi] = useState(0)
    const [fresh, setFresh] = useState(false)

    document.title = 'Fervm GdR - Zaino'

    useEffect(() => {
        axios.get(API_URL.CHARACTER + "/getinventory/" + character.characterId,{
            headers: {
              'Authorization': 'Fervm '+getJwt()
            }})
            .then(({ data }) => {
                setLoading(true)

                if (data && data.inventory) {
                    setInventory(data.inventory.items)
                    setSesterzi(data.inventory.gold)
                }
                setFresh(false)
                setLoading(false)
            }).catch(err => {
                console.log("errore di connessione all'inventario", err)
            })
    }, [character.characterId, fresh])

    if (isloading) { return (<div className="loading w3-card">Loading Inventory</div>) }

    return (
        <DndProvider backend={HTML5Backend}>
            <div style={{ display: 'flex', width: '100%' }}>
                <CharacterInventoryEquipped character={character} setFresh={setFresh} inventoryItems={inventory} setDetail={setDetail}/>
                <div className="w3-container w3-third">
                    {/*i Drop Items to be downloaded from server */}
                    <h3 className="w3-header">Inventario</h3>
                    <div className="banca">
                        <div className="box-sesterzio"></div>
                        <>Sesterzi In tuo possesso: <h4 style={{marginLeft:'10px'}}>{sesterzi}</h4></>
                    </div>
                    <h4>Oggetti Posseduti:</h4>
                    <CharacterInventoryBag character={character} inventoryItems={inventory} setFresh={setFresh} setDetail={setDetail} />
                </div>

                <div className="w3-container w3-third">
                    {detail &&
                        <>
                            <button className='ctrl-btn-M' onClick={() => { setDetail(null) }}>Chiudi</button>
                            <ItemDetails owner={character} item={detail} />
                        </>
                    }
                </div>
            </div>
        </DndProvider>
    )
}

export default CharacterInventory;
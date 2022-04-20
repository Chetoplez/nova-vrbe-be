import { ItemTypes } from "./Constants"
import DragItem from "./DragItem"
import DropArea from "./DropArea"

const CharacterInventoryEquipped = ({ character, inventoryItems, setDetail, setFresh }) => {
    
    const renderEquippedItems = (elemType) => {
        // console.log("INVENTORY",inventoryItems)
        // console.log("elemType",elemType)
        if (inventoryItems.length > 0) {
            return inventoryItems.filter(item => item.bodyPart === elemType && item.inUse)
            .map((item)=><DragItem key={item.id} item={item} characterId={character.characterId} setDetail={setDetail}></DragItem>)
             }
            return <></>
    }

    const renderDropArea = (bodyPart) =>
        <DropArea setFresh={setFresh} type={bodyPart} kind="equip">
            {renderEquippedItems(bodyPart)}
        </DropArea>

    return (
        <div className="w3-container w3-third" style={{ flexGrow: '1' }}>
            <h1 className="w3-header">{character.characterName}</h1>
            <div className="info-pg">
                <h3>Indossato</h3>
                <div className="dropArea">
                    {renderDropArea(ItemTypes.HEAD)}
                    {renderDropArea(ItemTypes.CHEST)}
                    <div style={{ display: "flex", width: "270px", justifyContent: "space-between" }}>
                        {renderDropArea(ItemTypes.RIGHT_HAND)}
                        {renderDropArea(ItemTypes.LEFT_HAND)}
                    </div>
                    {renderDropArea(ItemTypes.FEET)}
                </div>
            </div>
        </div>
    )
}

export default CharacterInventoryEquipped
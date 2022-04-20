import { InventoryCategories, ItemTypes } from "./Constants"
import DragItem from "./DragItem"
import DropArea from "./DropArea"

const CharacterInventoryBag = ({ character, inventoryItems, setDetail, setFresh }) => {
    const getCategoryItem = (inventoryCategory) => {
        const categoryMapping = {
            WEAPON: 'Armi',
            FOOD: 'Cibo',
            ARMOR: 'Armature',
            PET: 'Animali',
            GENERIC: 'Generici'
        }

        return categoryMapping[inventoryCategory] ? categoryMapping[inventoryCategory] : undefined
    }

    const renderBagItemsByCategory = inventoryCategory => {
        if (inventoryItems.length > 0) {
            return inventoryItems.filter((item) => item.category === inventoryCategory && !item.inUse)
                .map((item) => <DragItem key={item.id} item={item} setDetail={setDetail} characterId={character.characterId} />)
        }

        return []
    }

    return InventoryCategories.map((inventoryCategory) => {
        return (
            <div key={inventoryCategory} className="dragArea">
                <h5>{getCategoryItem(inventoryCategory)}</h5>
                <DropArea setFresh={setFresh} kind="bag" type={ItemTypes.GENERIC}>
                    {renderBagItemsByCategory(inventoryCategory)}
                </DropArea>
            </div>
        )
    })
}

export default CharacterInventoryBag
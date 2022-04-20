import CharacterStatusHealth from "./CharacterStatusHealth"
import CharacterStatusStats from "./CharacterStatusStats"

const CharacterStatus = ({ character, showInventory, onToggleCharacterView }) => {
    return (
        <div className="character-status">
            <CharacterStatusHealth health={character.health} healthStatus={character.healthStatus} characterName={character.name} characterImage={character.characterImg} />
            <button className='ctrl-btn-S' onClick={() => onToggleCharacterView(!showInventory)}>
                {showInventory ? 'Profilo' : 'Zaino'}
            </button>
            <CharacterStatusStats stats={character.stats} />
        </div>
    )
}

export default CharacterStatus

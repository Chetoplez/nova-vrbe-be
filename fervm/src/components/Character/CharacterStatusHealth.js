import { buildStyles, CircularProgressbarWithChildren } from "react-circular-progressbar"

const CharacterStatusHealth = ({ health, healthStatus, characterName, characterImage }) => {
    const STYLE = {
        progressBarStyle: buildStyles({
            strokeLinecap: 'butt',
            textSize: '16px',            
            pathTransitionDuration: 0.5,
            pathColor: `#63C132`,
            textColor: '#f88',
            trailColor: 'transparent',
            backgroundColor: '#3e98c7'
        }),
        profileImage: {
            width: '155px',
            height: '155px',
            borderRadius: '50%'
        },
        health: {
            display: "flex",
            justifyContent: 'space-between',
            padding: '0px 15px 0px 15px'
        }
    }

    return (
        <>
        <div className="board-profilo">
            <div className="progressSalute">
                <CircularProgressbarWithChildren value={health}
                    strokeWidth="4"
                    styles={STYLE.progressBarStyle}>
                    <img alt={characterName} src={characterImage} style={STYLE.profileImage} />
                </CircularProgressbarWithChildren>
                </div>
        </div>
            <div className='salute'>
                <div style={STYLE.health}>
                    <span>Salute: {health} %</span>
                    <span>{healthStatus}</span>
                </div>
            </div>
        </>
    )
}

export default CharacterStatusHealth
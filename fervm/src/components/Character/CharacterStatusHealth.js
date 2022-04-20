import { buildStyles, CircularProgressbarWithChildren } from "react-circular-progressbar"

const CharacterStatusHealth = ({ health, healthStatus, characterName, characterImage }) => {
    const STYLE = {
        progressBarStyle: buildStyles({
            strokeLinecap: 'butt',
            textSize: '16px',
            pathTransitionDuration: 0.5,
            pathColor: `rgba(11, 164, 0)`,
            textColor: '#f88',
            trailColor: '#ffffff',
            backgroundColor: '#3e98c7'
        }),
        profileImage: {
            width: '185px',
            height: '185px',
            borderRadius: '50%'
        },
        health: {
            display: "flex",
            justifyContent: 'space-between',
            padding: '0px 15px 0px 15px'
        }
    }

    return (
        <div className="progressSalute">
            <CircularProgressbarWithChildren value={health}
                strokeWidth="4"
                styles={STYLE.progressBarStyle}>
                <img alt={characterName} src={characterImage} style={STYLE.profileImage} />
            </CircularProgressbarWithChildren>
            <div className='salute'>
                <div style={STYLE.health}>
                    <span>Salute: {health} %</span>
                    <span>{healthStatus}</span>
                </div>
            </div>
        </div>
    )
}

export default CharacterStatusHealth
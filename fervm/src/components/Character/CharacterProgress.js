import { React } from 'react'
import { makeStyles } from '@material-ui/core/styles';
import { LinearProgress } from '@material-ui/core';
const useStyles = makeStyles({
    myProgress: {
        height: '10px',
        backgroundColor: '#FFFFFF'
    },
    mybarColor: {
        backgroundColor: '#FFBB00'
    }
})


function CharacterProgress ({ character }){
const classes = useStyles();
    return(
        
            <div className="exp-pg-container">
                            <div className="pg-progress">
                                <strong>Livello: {character.level.level} </strong>
                                <div><span>{character.level.experience}</span> / <strong>1500</strong></div>
                            </div>
                            {/* check the way we calculate the experience and the level. */}
                            <LinearProgress
                                variant='determinate'
                                value={(character.level.experience * 100) / 1500}
                                classes={{ root: classes.myProgress, colorPrimary: classes.myProgress, 
                                barColorPrimary: classes.mybarColor }}
                            />
                            <span>Esperienza: {character.level.totalExperience}</span>
                        </div>
        
    )
} export default CharacterProgress;
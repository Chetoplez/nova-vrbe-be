/**
 * Render the Curriculm of a characther, nothing special
 */

import React from 'react';
import { Avatar } from '@material-ui/core';
import { makeStyles } from '@material-ui/core/styles';
const useStyles = makeStyles({
   
    smallAvatar:{
        width: '30px',
        height: '30px'
    },
    smallFallBack:{
        width: '60%',
        height: '60%'
    }
})
function CharacterCV(props) {
    const classes = useStyles();
    return(
        <div className="cv-pg w3-card w3-padding">
            {props.curriculumPg.map((job,index) => {
                return (
                    <div key={index} className="w3-container" style={{ display: 'flex', marginTop: "10px" }}>
                        <Avatar classes={{ root: classes.smallAvatar, fallback: classes.smallFallBack }}
                            src={job.img}
                            sizes='small' alt={job.roleName}>
                        </Avatar>
                        <div className="w3-third riga-cv">{job.roleName}</div> <span>dal</span>
                        <div className="w3-quarter riga-cv">{job.enrolmentDate}</div>
                    </div>
                )
            })}
        </div>
    )

}export default CharacterCV;
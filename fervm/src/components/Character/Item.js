import {React} from 'react'
import Tooltip from '@material-ui/core/Tooltip';

function Item(props) {
    let img = props.item.url;
    return(
        <>
            <Tooltip title={props.item.name} placement="top-end">
            <div className="drag-item" style={{backgroundImage: `url(${img})`, backgroundSize:'cover'}}>
                
            </div>
            </Tooltip>
        </>
    )
} export default Item;
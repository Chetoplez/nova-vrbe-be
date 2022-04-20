import axios from 'axios';
import { useContext } from 'react';
import { useDrop } from 'react-dnd';
import { API_URL, getJwt } from '../../utils/api';
import { userContext } from '../../utils/userContext';
import { ItemTypes } from './Constants';
import store from 'store';
import './DropArea.css';

function DropArea({ children, type, kind, setFresh }) {
  const mainContext = useContext(userContext)

  const [collectedProps, drop] = useDrop(
    () => ({
      accept: "Inventario",
      drop: (droppedItem) => toggleEquippement(droppedItem),
      canDrop: (droppedItem) => isValidItem(droppedItem),
      collect: (monitor) => {
        return {
          isOver: !!monitor.isOver()
        }
      }
    })
  )

  const isValidItem = (item) => {
    // console.log(item.bodyPart)
    // console.log(type)
    if (kind !== 'bag') {
      
      return (type === item.bodyPart)
    }

    return true
  }

  const toggleEquippement = ({ id }) => {
    const payload = {
      characterId: mainContext.user.characterId,
      itemId: id,
      remove: kind === 'bag' ? true : false
    }

    axios.post(API_URL.CHARACTER + "/equip", payload,{
      headers: {
        'Authorization': 'Fervm '+getJwt()
      }})
      .then(resp => {
        if (resp.data.equipped) {
          setFresh(true)
          // console.log('Is item equipped?', resp.data.equipped)
        }
      }).catch(console.log)
  }

  return (
    <div
      ref={drop}
      className={type === ItemTypes.GENERIC ? 'zaino' : type + ' drop-area'}
      style={{ backgroundColor: collectedProps.isOver ? 'burlywood' : 'transparent' }}
    >
      {children}
    </div>
  )

}

export default DropArea;
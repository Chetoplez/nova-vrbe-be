import Tooltip from '@material-ui/core/Tooltip';
import { useContext } from 'react';
import { useDrag } from 'react-dnd';
import { userContext } from '../../utils/userContext';

function DragItem({ item, characterId, setDetail}) {
  const mainContext = useContext(userContext)

  const [{ isDragging }, drag] = useDrag(() => ({
    type: "Inventario",
    item,
    canDrag: () => isValidItem(),
    collect: (monitor) => ({
      isDragging: !!monitor.isDragging()
    })
  }))

  const isValidItem = () => {
    return item.equipment && characterId === mainContext.user.characterId;
  }

  return (
    <div ref={drag}
      item={item}
      style={{
        opacity: isDragging ? 0.5 : 1,
        cursor: isValidItem ? 'grab' : 'default'
      }}
      onClick={() => setDetail(item)}>
      <Tooltip title={item.name} placement="top-end">
        <div className="drag-item" style={{ backgroundImage: `url(${item.url})`, backgroundSize: 'cover', backgroundPosition: 'center' }} />
      </Tooltip>
    </div>
  )
} export default DragItem
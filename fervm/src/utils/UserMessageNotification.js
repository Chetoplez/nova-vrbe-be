import React from 'react';
import useMessage from '../utils/useMessage'
import { Modal, Box, Typography } from "@material-ui/core";

function UserMessageNotifcation() {
  const { message, removeMessage } = useMessage();
    
  const handleSubmit = () => {
    removeMessage();
  };

  return (
    
    <Modal
      open={!!message} 
      aria-labelledby="modal-modal-title"
      aria-describedby="modal-modal-description"
      style = {{backgroundColor: 'transparent'}}
    >
        <Box style={{
          position: 'absolute',
          top: '50%',
          left: '50%',
          transform: 'translate(-50%, -50%)',
          width: '400px',
          backgroundColor: 'whitesmoke',
          border: '2px solid #000',
          padding: '10px',
        }} >
          <Typography id="modal-modal-title" variant="h6" component="h2">
            Avviso:
          </Typography>
          { message && message.message &&  <Typography id="modal-modal-description" sx={{ mt: 2 }}>{message.message}
          </Typography>
          }
            
          <button className='primary-btn-M' style={{float: 'right'}} onClick={handleSubmit}>
            OK
          </button>
        
        </Box>
       
   </Modal>
   
  )
} export default UserMessageNotifcation;
import { useContext } from 'react';
import { messageContext } from '../utils/messageContext.js';

function useMessage() {
  const { message, addMessage, removeMessage } = useContext(messageContext);
  return { message, addMessage, removeMessage };
}

export default useMessage;
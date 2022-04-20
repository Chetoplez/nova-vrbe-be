
import React, { useState, useCallback } from 'react';

export const messageContext = React.createContext({
  message: null,
  addMessage: () => {},
  removeMessage: () => {}
});

export default function UserMessageProvider({ children }) {
  const [message, setMessage] = useState(null);

  const removeMessage = () => setMessage(null);

  const addMessage = (message, status) => setMessage({ message, status });

  const contextValue = {
    message,
    addMessage: useCallback((message, status) => addMessage(message, status), []),
    removeMessage: useCallback(() => removeMessage(), [])
  };

  return (
    <messageContext.Provider value={contextValue}>
      {children}
    </messageContext.Provider>
  );
}
import React, {useEffect, useState} from 'react'; 
import IconButton from '@material-ui/core/IconButton';
import DoubleArrowOutlinedIcon from '@material-ui/icons/DoubleArrowOutlined';

/**
 * This component shoul work like the bottom arrow button on telegram, that notifies you the new messages and 
 * scroll you to the bottom when clicke, but it doesn't work. I cannot get the event on a new message, or the new child append on 
 * the message list
 * 
 */    
function ScrollBtn(){ 
  
    useEffect(()=>{
        document.getElementById("activeChat").addEventListener("scroll", toggleVisible) 
    },[])

  const [visible, setVisible] = useState(false) 
    
  const toggleVisible = () => { 
    const scrolled = document.getElementById("activeChat").scrollHeight
    
    if (scrolled > 0){ 
      setVisible(true) 
    }  
    else if (scrolled <= 0){ 
      setVisible(false) 
    } 
  }; 
    
  const scrollToBottom = () =>{ 
    console.log("do something", document.documentElement.scrollHeight)
    document.getElementById("activeChat").scrollTo({ 
      top: document.getElementById("activeChat").scrollHeight, 
      behavior: 'smooth'
    }); 
  }; 
    
//   document.getElementById("activeChat").addEventListener("scroll", toggleVisible)
    
  return ( 
    <IconButton onClick={scrollToBottom} style ={{color: '#4998ec' , display: visible ? 'inline' : 'none', position: 'absolute',
       bottom:'15%',left:'56%' }}>
         <DoubleArrowOutlinedIcon style={{ transform: 'rotate(90deg)' }} />
    </IconButton>
  ); 
} 
    
export default ScrollBtn
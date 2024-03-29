import React, { useState, useContext, useEffect } from 'react';
import HomeNav from './Navbar/HomeNav';
import Login from './Login';

import '../components/Home.css';
import { userContext } from '../utils/userContext';
import { useNavigate } from 'react-router-dom'
import logo from '../img/logo/fervm_logo.svg'


/**
 * I'm updating this component to include the subscription of a new user and the creation of a new Character
 * 
 */
function Home() {
  const mainContext = useContext(userContext);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    var body = document.getElementById("body")
    body.classList.add("home-img")
    setLoading(false);
    // const checkAuth = async () => {
    //   let res = mainContext.tryLoginUser()
    //   if (res) {
    //     setLoading(false);
    //   } else {
    //     // redirect to login page
    //   }
    // }

    // checkAuth();
  }, [])

 

  const registration = () => {navigate('/subscribe')}

  if (loading) {
    return <div className="loading w3-card">loading</div>;
  }


  return (
    <>
      <HomeNav />
      <header style={{ textAlign: 'center' }}>
        <h3>Il tuo destino ti aspetta alle porte di</h3>
         <h1 className='game-title' style={{ fontSize: '100px' }}>
          <img alt='logo' src={logo} width='500px'/>
         </h1> 
        
      </header>
      <div className="home-content">

        <div className="w3-container">
          <section className="game-content w3-card">
            <p>È giunto il tempo di vestire la toga virile, scalare le tappe che il mos maiorum ti impone!</p>
            <p>Servire sotto le insegne della legione o tra le fila del sacerdoti del Clero? Darsi agli studi della medicina
              o ai commerci per la colonia? </p>
            <p>Stringi alleanze, otteni consensi e il tuo nome sarà ricordato in tutta la Repubblica!</p>
          </section>
        </div>
        <div className="w3-container w3-half SpallaSx">
          <Login />
        </div>
      </div>
      <footer className='w3-bottom w3-center' style={{backgroundColor: 'rgb(255 255 255 / 32%)', height:'25px', width:'90%'}}>
        <a style={{fontSize: '12px'}}  href="https://www.freepik.com/vectors/wood-button">Wood button vector created by upklyak - www.freepik.com</a>
      </footer>
    </>
  );
} export default Home;
import React, { useState, useContext, useEffect } from 'react';
import HomeNav from './Navbar/HomeNav';
import Login from './Login';

import '../components/Home.css';
import { userContext } from '../utils/userContext';
import { useNavigate } from 'react-router-dom'



/**
 * I'm updating this component to include the subscription of a new user and the creation of a new Character
 * 
 */
function Home() {
  const mainContext = useContext(userContext);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    const checkAuth = async () => {
      let res = mainContext.tryLoginUser()
      if (res) {
        setLoading(false);
      } else {
        // redirect to login page
      }
    }

    checkAuth();
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
        <h1 style={{ fontSize: '60px' }}>FERVM</h1>
      </header>
      <div className="home-content">

        <div className="w3-container">
          <section className="game-content w3-card">
            <p>E' giunto il tempo di vestire la toga virile, scalare le tappe che il mos maiorum di ti impone!</p>
            <p>Servire sotto le insegne della legione , o tra le fila del sacerdoti del Clero? Darsi agli studi della medicina
              o ai commerci per la colonia? </p>
            <p>Stringi alleanze, otteni consenti e il tuo nome sarà ricordato in tutta la Repubblica!</p>
          </section>
        </div>
        <div className="w3-container w3-half SpallaSx">
          <Login />
        </div>
      </div>
    </>
  );
} export default Home;
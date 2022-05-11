import React, {useState, useContext} from 'react'
import { API_URL, getJwt } from '../utils/api'
import store from 'store'
import axios from 'axios';
import { TextField } from '@material-ui/core';
import { userContext } from '../utils/userContext';
import { useNavigate } from 'react-router-dom'
import { useForm } from "react-hook-form";
import '../components/Home.css';


function Login() {

    const [error, setError ] = useState(null);
    const [step, setStep] = useState(0)
    const mainContext = useContext(userContext);
    const navigate = useNavigate();
    const { register, handleSubmit,  formState: { errors } } = useForm({mode: 'all'});
  
    
    const submitForm = (evt) => {
               
        switch(step){
          case 0:
            axios.post(API_URL.USER+"/checkmail", {email: evt.username })
            .then(resp=>{
              if(resp.data.presente)
                setStep(1);
              else navigate('/subscribe')
            }).catch(err=>{
              console.log("CheckEmail", err);
            })
            break;
          case 1:
            var payload = {
              email: evt.username,
              psw: evt.password
            }

            axios.post(API_URL.USER + "/login", payload)
              .then(resp => {
                if (resp.data.success) {
                  //console.log(resp.data)
                  store.set('isLogin', true);
                  store.set('jwt', resp.data.jwt)
                  mainContext.setRoles([resp.data.role])
                  store.set('roles',[resp.data.role])
                  login(resp.data.userId)
                } else {
                  setError("Email e/o Password errate, riprova :)")
                  setStep(0)
                }
              }).catch(err => {
                console.log(err)
              })
              break;
          default:
            setStep(0)
        }

        
    
      }
    
    const login = (userId)=>{
       
        axios.get(API_URL.CHARACTER+"/getcharacter/"+userId , {
          headers: {
            'Authorization': 'Fervm '+getJwt()
          }})
        .then(resp =>{
          
          if(!resp.data.newpg){
            var user = {
              characterId: resp.data.character.characterId,
              characterName: resp.data.character.characterName,
              characterSurname: resp.data.character.characterSurname,
              characterImg: resp.data.character.characterImg
            }
            store.set('user',user);
            mainContext.setUser(user);
            mainContext.setLoggedIn(true);
            
            axios.patch(API_URL.PRESENTI+"/getonline",{characterId: resp.data.character.characterId, online: true} ,
            {
              headers: {
                'Authorization': 'Fervm '+getJwt()
              }} )
            navigate('/game/mainmap');
          }else {
            navigate("/subscribe/newcharacter/"+userId)
          }
        }).catch(err=>{
          console.log("login", err)
        })
      }

    function renderLoginButton(){
      if(step === 0){
        return(
          <button type='submit' className='primary-btn-L' value='checkmail' >
            Entra / Registrati
          </button>
        )
      }else if(step === 1) {
        return(
          <button type='submit' className='primary-btn-L' value='login' >
            Entra!
          </button>
        )
      }
    }

    return(
        <>
            <form onSubmit={handleSubmit(submitForm)} >
            <div className='home-board'>
              <h4>Entra a FERVM!</h4>
              {step === 0 && <div>
                <TextField placeholder="Inserisci la tua email" type="text" name='username' id='checkmail'
                {...register("username",
                { required: {value:true, message: 'Questo campo è obbligatorio'} , 
                pattern:{
                  value: /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/,
                  message: "Indirizzo email non valido"
               } })} />
              <p className="errorMessage">{errors.username?.type === 'pattern' ? errors.username.message : ''}</p>
              <p className="errorMessage">{errors.username?.type === 'required' ? errors.username.message : ''}</p>
              </div>}
              {step === 1 && <div style={{ marginTop: 10 }}>
              <TextField placeholder="Inserisci la password" type="password" autoComplete='current-password' name='password'
                {...register("password", { required: {value:true, message: 'Questo campo è obbligatorio'} })}
            />
            <p className="errorMessage">{errors.password?.type === 'required' ? 'Questo campo è obbligatorio' : ''}</p>  
                
              </div>}
              
            {error && (<p className='errorMessage'>{error}</p>)}
            </div>
            <p>
             {renderLoginButton()}
            </p>
            
          </form>

        </>
    )
} export default Login;
import React  from "react";
import { TextField } from '@material-ui/core';
import { useNavigate } from 'react-router-dom'
import axios from "axios";
import { API_URL } from "../../utils/api";
import HomeNav from '../Navbar/HomeNav'
import { useForm } from "react-hook-form";

import  './Subscribe.css'



/**
 *  
 * @returns This component renders the subcribe form. I'm very bad at form in React, I need to study well this part. 
 * This is only temporary to have something that create a new User and a new Character. It works only if you know how
 */
function Subscribe() {
  const navigate = useNavigate();
  const { register, watch, handleSubmit,  formState: { errors } } = useForm({mode: 'all'});


  const submitForm = (evt) => {
    var payload = {
      userPojo: {
        email: evt.email,
        password: evt.password,
        nickname: evt.nickname

      }
    }

    axios.put(API_URL.USER + "/create", payload)
      .then(resp => {
        //console.log("genericUser", resp)
        if (resp.status === 200 && resp.data.id !== undefined) {
          navigate("/subscribe/newcharacter/" + resp.data.id)
        }
      }).catch(err => {
        console.log(err)
      })


  }

  return (
    <>
      <HomeNav />
      <div className="w3-center">
        <header>
          <h3>Iscriviti a Fervm! </h3>
        </header>
        <form onSubmit={handleSubmit(submitForm)}>
          <div className="margin-form">
            <h3>Email</h3>
            <TextField placeholder="Email" type="email" name="email"
              {...register("email", { required: {value:true, message: 'Questo campo è obbligatorio'} , 
                pattern:{
                  value: /^\w+([-]?\w+)*@\w+([-]?\w+)*(\w{2,3})+$/,
                  message: "Indirizzo email non valido"
               } })}
            />
          <p className="errorMessage">{errors.email?.type === 'pattern' ? errors.email.message : ''}</p>
          <p className="errorMessage">{errors.email?.type === 'required' ? errors.email.message : ''}</p>
          </div>
          <div className="margin-form">
            <h3>Nickname</h3>
            <TextField placeholder="Nickname" type="text" name="nickname"
              {...register("nickname", { required: {value:true, message: 'Questo campo è obbligatorio'} })}
            />
          <p className="errorMessage">{errors.email?.type === 'required' ? errors.email.message : ''}</p>
          </div>
          <div className="margin-form">
          <h3>Password</h3>
            <TextField placeholder="Password" type="password" name="password"
              {...register("password", { required: {value:true, message: 'Questo campo è obbligatorio'} , minLength: 6})}
            />
            <p className="errorMessage">{errors.password?.type === 'minLength' ? 'La password deve avere almeno 6 charatteri' : ''}</p>
          </div>
          <div className="margin-form">
          <h3>Conferma Password</h3>
            <TextField placeholder="Conferma Password" type="password" name="repPassword"
              {...register("repPassword", { required:{value:true, message: 'Questo campo è obbligatorio'} , minLength: {value: 6, message: 'La password deve avere almeno 6 charatteri' }, validate: value => value === watch().password || 'Le password Non Corrispondono'})}
            />
          <p className="errorMessage">{errors.repPassword?.type === 'validate' ? errors.repPassword.message : ''}</p>
          <p className="errorMessage">{errors.repPassword?.type === 'minLength' ? errors.repPassword.message : ''}</p>
          </div>
          <p>
            <button className='main-btn-M' type="submit"> Registrati</button>
            <button type="button" className="main-btn-M" onClick={() => { navigate("/") }}>Indietro</button>
          </p>
        </form>
        
      </div>
      {/* <pre>{JSON.stringify(watch(), null, 4)}</pre> */}
    </>
  )
} export default Subscribe;
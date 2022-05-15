import React, { useState } from "react";
import axios from "axios";
import { useForm } from "react-hook-form";


import DominusFrame from "./DominusFrame";
import DominaFrame from "./DominaFrame";
import CharacterInfo from "./CharacterInfo";
import CharacterStat from "./CharacterStat";
import CharacterNomina from "./CharacterNomina";
import NomiFrame from './NomiFrame';
import StatChart from "./StatChart";
import HomeNav from "../Navbar/HomeNav";
import { useNavigate, useParams } from "react-router-dom";
import { API_URL, getJwt } from "../../utils/api";
import useMessage from "../../utils/useMessage";



const MAX_STEP = 3;

function CharacterCreation(){
   const [stepForm, setStepForm] = useState(0);
   
   const param = useParams()
   const [bucket, setBucket] = useState(10);
   const [validName, setValidName] = useState(false)
   const navigate = useNavigate();
   const { addMessage } = useMessage(); 

   const { register, watch, handleSubmit,  formState: { errors } } = useForm({mode: 'all'});
   const [stats, setStats] = useState([
     {
       statName: 'FORZA',
       baseStat: 1,
       modified: 0
     },
     {
      statName: 'DESTREZZA',
      baseStat: 1,
      modified: 0
    },
    {
      statName: 'COSTITUZIONE',
      baseStat: 1,
      modified: 0
    },
    {
      statName: 'SAGGEZZA',
      baseStat: 1,
      modified: 0
    },
    {
      statName: 'INTELLIGENZA',
      baseStat: 1,
      modified: 0
    }
   ])
   const nextStep = () =>{
     if(stepForm+1 <=2)
     setStepForm(cur => cur + 1);
   }

   const prevStep = ()=>{
     if( stepForm - 1 >= 0)
        setStepForm(cur => cur - 1);
   }

   function renderButton(){

    switch(stepForm){
      case 0:
        return (
          <button
          className='primary-btn-M'
          type="button"
          onClick={nextStep}>
        Continua
      </button>
        );
      case 1:
        return (<div>
          <button className="primary-btn-M" 
              type="button"
              onClick={prevStep}>Indietro</button>
          <button
            className='primary-btn-M'
            type="button"
            onClick={nextStep}
            disabled={!validName}>
          Continua
        </button>
        </div>)
        case 2:
          return(
          <div style={{display: 'flex', justifyContent: 'space-around'}}>
          <button className="primary-btn-M" 
          type="button"
          onClick={prevStep}>Indietro</button> 
          <button
            className='primary-btn-M'
            type="submit" 
            disabled={bucket > 0}>
            Crea!
          </button>
      </div>)
      default:
        return undefined;  
    }
   }

     
   const submitForm = (values) =>{
     var payload = {
       userId: param.newId,
       character: {
         characterName: values.characterName,
         characterSurname: values.characterCognomen,
         gender: values.gender ? values.gender : "FEMMINA",
         status: values.status ? values.status : "PLEBEO",
         stats: stats
       }
     }
     axios.put(API_URL.CHARACTER+"/", payload,{
      headers: {
        'Authorization': 'Fervm '+getJwt()
      }})
     .then(resp => {
       if(resp.data.success)
        addMessage("Nuovo Personaggio Creato!")
        navigate("/")
     }).catch(err=>{
       console.log("creationCharacter",err);
     })

   } 

    function checkNome(){
    var data = {
        nome: watch().characterName,
        cognome: watch().characterCognomen
    }

    axios.post(API_URL.CHARACTER + '/checknomevalidity', data , {
        headers: {
          'Authorization': 'Fervm '+getJwt()
    }}).then(resp=>{
        if(resp.data.valid) {
           setValidName(true);
        }
    })
}
  
      
   return(
      <>
      <HomeNav />
        <form onSubmit={handleSubmit(submitForm)}  style={{ display: "flex" }}>
          <div className='form-character colonna-sx w3-center'>
          <div className="contenitori">
            <header className='w3-header'> 
              <h2>
                Ave {(watch().characterName || watch().characterCognomen) ? watch().characterName+' '+watch().characterCognomen : 'Straniero' }! <br />
                Sei nuovo da queste parti...
                <br /> Chi sei?
              </h2>
            </header>
            <h4> Passo {stepForm +1 } di {MAX_STEP}</h4>
            {stepForm === 0  && (<CharacterInfo errors={errors} register={register} />)}
            {stepForm === 1  && (<CharacterNomina errors={errors} register={register} checkNome={checkNome} validName={validName} />)}
            {stepForm === 2 && (<CharacterStat bucket={bucket} setBucket={setBucket} stats={stats} setStats={setStats} />)}
            <br />
            {renderButton()}
            </div>
          </div>
          {stepForm === 0 && (
            <div className='colonna-dx'>
              <div className="contenitori">{watch().gender === "MASCHIO" ? <DominusFrame /> : <DominaFrame />}</div>
            </div>)}
            {stepForm === 1 && (
            <div className='colonna-dx'>
              <div className="contenitori">{<NomiFrame />}</div>
            </div>)}
            {stepForm === 2 && (
            <div className='colonna-dx'>
              <div className="contenitori"><StatChart stats={stats}/></div>
            </div>)}
            {/* <pre>{JSON.stringify(watch(), null, 4)}</pre> */}
        </form>
      </>  

    )

} export default CharacterCreation;
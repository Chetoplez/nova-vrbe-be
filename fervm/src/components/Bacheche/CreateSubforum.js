import React, { useEffect, useState, useContext } from 'react'
import { TextField, FormControl, Select, MenuItem, InputLabel, Input } from "@material-ui/core";
import { useForm } from "react-hook-form";
import axios from 'axios';
import { API_URL, getJwt } from '../../utils/api';
import { userContext } from '../../utils/userContext';
import { useNavigate,  useParams } from 'react-router-dom'

function CreateSubforum(){
    
    const [guildList, setGuildList] = useState([])
    const [ownedBy, setOwnedBy] = useState(-1);
    const [rankLevel, setRanklevel] = useState(10)
    const { register, watch, handleSubmit,  formState: { errors } } = useForm({mode: 'all'});
    const mainContext = useContext(userContext);
    const navigate = useNavigate();
    
    const param = useParams()

    useEffect(()=>{
        axios.get(API_URL.GUILD+"/getall", {
            headers: {
                Authorization: "Fervm "+getJwt()
            }
        }).then(resp=>{
                setGuildList(resp.data.guilds)
        })
    }, [])
    
    const submitForm = (values) =>{
        var payload = {
          chId: mainContext.user.characterId,
          subForum: {
            name: values.name,
            adminOnly: values.adminOnly,
            subforumType: values.forumType[0],
            ownedBy: ownedBy,
            rankVisibility: rankLevel,
            forumId: param.idForum
          }
        }
        console.log("payload", payload)
        axios.put(API_URL.SUBFORUM+"/create", payload,{
         headers: {
           'Authorization': 'Fervm '+getJwt()
         }})
        .then(resp => {
           navigate("/game/forum/subforums/"+param.idForum+"/posts/"+resp.data.subforumId , )
        }).catch(err=>{
          console.log("CreateSubForum",err);
        })
   
      } 

    const handleRank = event => {
        console.log(event.target.value)
        setRanklevel(event.target.value);
       };

    const handleChange = event => {
        console.log(event.target.value)
        setOwnedBy(event.target.value);
       };

    return(
        <>
        <header className='w3-center'>
            <h1>Nuova Sezione</h1>
        </header>
        <form onSubmit={handleSubmit(submitForm)} className="w3-card w3-padding w3-container" style={{width:'50%', margin:'25px auto'}}>
            <h3>Nome Sezione</h3>
            <TextField style={{minWidth:"100px"}} placeholder="Nome Sezione"
                name="name"
                {...register("name", { required: true, minLength: 3, maxLength: 25 })} /><br></br>
            
            <input className="w3-margin" type="checkbox" value="true" name='adminOnly'
                {...register("adminOnly")}
            />Solo Admin
           
            <div>
                <label>Tipo Sezione:</label><br></br>
                <input className="w3-margin" type="checkbox" value="ON" name='forumType' {...register("forumType")}/>ON Game
                <input className="w3-margin" type="checkbox" value="OFF" name='forumType' {...register("forumType")}/>OFF Game
            </div>
            <FormControl>
                <InputLabel htmlFor="nome-gilda">Livello di Visualizzazione </InputLabel>
                <Select
                    name='level'
                    style={{minWidth:'200px'}}
                    value = {rankLevel}
                    input={<Input  name="yteref" id="nome-gilda" />}
                    onChange = {handleRank}
                    >
                    <MenuItem value='10' name="Pubblica">Tutti</MenuItem>
                    <MenuItem value='20' name="PrimoGrado">Secondo Livello gilda</MenuItem>
                    <MenuItem value='30' name="Pubblica">Tero Livello Gilda</MenuItem>
                    <MenuItem value='40' name="Pubblica">Quarto Livello Gilda</MenuItem>
                    <MenuItem value='50' name="Pubblica">Capo gilda</MenuItem>                    
                </Select>
            </FormControl>
            <FormControl>
                <InputLabel htmlFor="nome-gilda">Appartiene a: </InputLabel>
                <Select
                    name='ownedBy'
                    style={{minWidth:'200px'}}
                    value = {ownedBy}
                    input={<Input  name="yteref" id="nome-gilda" />}
                    onChange = {handleChange}
                    >
                    <MenuItem value="-1" name="Pubblica">Pubblica</MenuItem>
                    {guildList.map((opt)=>{
                        return(
                            <MenuItem key={opt.id} value={opt.id} >{opt.name}</MenuItem>
                        );}
                    )}
                </Select>
            </FormControl>
            <p>
                <button type='submit' className='main-btn-M'>Crea</button>
                <button type='button' className='main-btn-M' onClick={()=>{navigate("/game/forum/subforums/"+param.idForum)}}>Indietro</button>
            </p>
            
        </form>
        </>
    )
} export default CreateSubforum;
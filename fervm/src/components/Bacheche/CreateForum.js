import React, { useEffect, useState, useContext } from 'react'
import { TextField, FormControl, Select, MenuItem, InputLabel, Input } from "@material-ui/core";
import { useForm } from "react-hook-form";
import axios from 'axios';
import { API_URL, getJwt } from '../../utils/api';
import { userContext } from '../../utils/userContext';
import { useNavigate, useLocation } from 'react-router-dom'

function CreateForum(){
    
    const [guildList, setGuildList] = useState([])
    const [ownedBy, setOwnedBy] = useState(-1);
    const { register, watch, handleSubmit,  formState: { errors } } = useForm({mode: 'all'});
    const mainContext = useContext(userContext);
    const navigate = useNavigate();

    useEffect(()=>{
        const checkAuth = async () => {
            let res = mainContext.tryLoginUser()
            if (res) {
            } else {
                // redirect to login page 
                navigate("/", { replace: true })
            }
        }
        checkAuth();
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
          forum: {
            name: values.name,
            description: values.description,
            adminOnly: values.adminOnly,
            forumType: values.forumType[0],
            ownedBy: ownedBy
          }
        }
        console.log("payload", payload)
        axios.put(API_URL.FORUM+"/create", payload,{
         headers: {
           'Authorization': 'Fervm '+getJwt()
         }})
        .then(resp => {
           navigate("/game/forum/subforums/"+resp.data.forumId)
        }).catch(err=>{
          console.log("CreateForum",err);
        })
   
      } 

    const handleChange = event => {
        //console.log(event.target.value)
        setOwnedBy(event.target.value);
        
       };


    return(
        <>
        <header className='w3-center'>
            <h1>Nuovo Forum</h1>
        </header>
        <form onSubmit={handleSubmit(submitForm)} className="w3-card w3-padding w3-container" style={{width:'50%', margin:'25px auto'}}>
            <h3>Nome Forum</h3>
            <TextField style={{minWidth:"100px"}} placeholder="Nome Forum"
                name="name"
                {...register("name", { required: true, minLength: 3, maxLength: 200 })} /><br></br>
            <h3>Descrizione Forum</h3>
            <TextField style={{minWidth:"100px"}} placeholder="Nome Forum"
                name="description"
                {...register("description", { required: true, minLength: 3, maxLength: 200 })} /><br></br>

            <input className="w3-margin" type="checkbox" value="true" name='adminOnly'
                {...register("adminOnly")}
            />Solo Admin
            <h3>Tipo Forum</h3>
            <div>
                <label>Tipo Forum:</label><br></br>
                <input className="w3-margin" type="checkbox" value="ON" name='forumType' {...register("forumType")}/>ON Game
                <input className="w3-margin" type="checkbox" value="OFF" name='forumType' {...register("forumType")}/>OFF Game
            </div>
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
                <button type='submit' className='primary-btn-M'>Crea</button>
                <button type='button' className='primary-btn-M' onClick={()=>{navigate("/game/forum/", {replace: false})}}>Indietro</button>
            </p>
            <pre>{JSON.stringify(watch(), null, 2)}</pre>
        </form>
        </>
    )
} export default CreateForum;
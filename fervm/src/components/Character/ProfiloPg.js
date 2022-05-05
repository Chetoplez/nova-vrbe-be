import React, { useEffect, useState, useContext } from 'react';
import { useParams, useNavigate } from 'react-router-dom'
import axios from 'axios'
import { makeStyles } from '@material-ui/core/styles';

import Icon from '@material-ui/core/Icon';
import IconButton from '@material-ui/core/IconButton';
import Avatar from '@material-ui/core/Avatar';
import 'react-circular-progressbar/dist/styles.css';
import './ProfiloPg.css'
import ArrowDropDownOutlinedIcon from '@material-ui/icons/ArrowDropDownOutlined';
import ClearIcon from '@material-ui/icons/Clear';
import parse from 'html-react-parser';

import CharacterInventory from './CharacterInventory';
import CharacterCV from './CharacterCV';
import CharacterProgress from './CharacterProgress'
import UserMsg from '../MainGui/UserMsg';
import store from 'store'
import { userContext } from '../../utils/userContext'
import { API_URL, getJwt, sanitazeHTML } from '../../utils/api';
import CharacterStatus from './CharacterStatus';

const useStyles = makeStyles({
    root: {
        padding: '0px'
    },
    mybarColor: {
        backgroundColor: '#FFBB00'
    }
})


/**
 * This is a big mess. In a single component function I have inserted a lot of things
 * 
 */

function ProfiloPg() {

    const classes = useStyles();
    const history = useNavigate()
    const [personaggio, setPersonaggio] = useState({}); //personaggio stand for Characher
    const [loading, setLoading] = useState(true);
    const [modDescrizione, setmodDescrizione] = useState(false); //description
    const [isOpen, setIsOpen] = useState(false);
    const [curriculumPg, setCurriculum] = useState([]);
    const [descrizione, setDescrizione] = useState("");
    const [storia, setStoria] = useState("");
    const [modStoria, setmodStoria] = useState(false);
    const mainContext = useContext(userContext)

    const [showInventory, setShowInventory] = useState(false);
    let { idPg } = useParams()

    useEffect(() => {
        document.title = 'Fervm GdR - Profilo'
        const checkAuth = async () => {
            let res = mainContext.tryLoginUser()
            if (res) {

            } else {
                // redirect to login page
                history("/")
            }
        }
        checkAuth();
        if (idPg === undefined) {
            idPg = store.get("idPg")

        } else {
            store.set("idPg", idPg)

        }

        setLoading(true)
        axios.get(API_URL.CHARACTER + "/getcharacter/" + idPg, {
            headers: {
                'Authorization': 'Fervm ' + store.get('jwt')
            }
        })
            .then((response) => {
                setPersonaggio(response.data.character);
                if (idPg === mainContext.user.characterId) {
                    var user = {
                        characterId: response.data.character.characterId,
                        characterName: response.data.character.characterName,
                        characterSurname: response.data.character.characterSurname,
                        characterImg: response.data.character.characterImg
                      }
                    mainContext.setUser(user);
                    store.remove('user');
                    store.set('user', user);

                }
                setDescrizione(response.data.character.description.description);
                setStoria(response.data.character.history.history);
                setLoading(false)

            }).catch(err => {
                console.log("errore di connessione al profilo.", err)

            })

        return () => { store.remove('idPg') }
    }, [idPg, personaggio.characterImg])

    /**
     * Opens the Curriculum of a Character after downloaded it from server
     */
    const openCV = () => {
        if (!isOpen) {
            axios.get(API_URL.GUILD + "/members/getcharactercv/characterId=" + personaggio.characterId, {
                headers: {
                    'Authorization': 'Fervm ' + store.get('jwt')
                }
            })
                .then(resp => {
                    setCurriculum(resp.data.curruculumPg);
                    setIsOpen(!isOpen)
                }).catch(err => {
                    console.log("errore caricamento CV", err);
                })
        } else { setIsOpen(!isOpen) }

    }

    const updateDescrizionePg = () => {
        //check if i'm modifyng or saving 
        if (modDescrizione) {
            //modifyng??, save on server! fire!
            var oldDesc = personaggio.description.description;
            var payload = {
                characterId: mainContext.user.characterId,
                newtext: sanitazeHTML(descrizione)
            }


            axios.patch(API_URL.CHARACTER + "/updatedescription", payload, {
                headers: {
                    'Authorization': 'Fervm ' + store.get('jwt')
                }
            })
                .then(resp => {
                    if (resp.data.changed) {
                        const oldOg = { ...personaggio }
                        oldOg.description.description = descrizione;
                        setPersonaggio(oldOg);

                    }

                }).catch(err => {
                    console.log("qualcosa non va!", err)
                    //Something wrong, set the old Description
                    setDescrizione(oldDesc)
                })

        }
        setmodDescrizione(!modDescrizione);
    }


    const updateStoriaPg = () => {
        //check if i'm modifyng or saving 
        if (modStoria) {
            //modifyng??, save on server! fire!
            var oldHystory = personaggio.history;

            var payload = {
                characterId: mainContext.user.characterId,
                newtext: sanitazeHTML(storia)
            }

            axios.patch(API_URL.CHARACTER + "/updatedehistory", payload, {
                headers: {
                    'Authorization': 'Fervm ' + getJwt()
                }
            }).then((resp) => {
                    if (resp.data.changed) {
                        const oldPg = { ...personaggio }
                        oldPg.history.history = storia;
                        setPersonaggio(oldPg);

                    }
                }).catch(err => {

                    setStoria(oldHystory)
                })

        }
        setmodStoria(!modStoria);
    }

    const handleDescr = (evt) => {
        setDescrizione(evt.target.value)
    }

    const handleStoria = (evt) => {
        setStoria(evt.target.value)
    }

    const toggleCharacterView = (shouldShowInventory) => {
        setShowInventory(shouldShowInventory)
    }

    if (loading) {
        return (<div className="loading w3-card">loading</div>)
    }

    return (
        <div className="contenitori" style={{ display: 'flex', justifyContent: 'space-between' }}>
            <CharacterStatus
                character={personaggio}
                showInventory={showInventory}
                onToggleCharacterView={toggleCharacterView}
            />

            {showInventory ?
                <CharacterInventory character={personaggio} /> :
                <div style={{ display: 'flex', width: '80%' }}>
                    {/* Personal information column , name, gender etc  */}

                    <div className="w3-container w3-third">
                        <h1 className="w3-header ">{personaggio.characterName +' '+personaggio.characterSurname} </h1>
                        <div className="info-pg">
                            <div>
                                <div className="w3-third"><strong>Sesso</strong></div>
                                <div>{personaggio.gender}</div>
                            </div>
                            <div>
                                <div className="w3-third"><strong>Status</strong></div>
                                <div>{personaggio.status}</div>
                            </div>
                            <div>
                                <div className="w3-third"><strong>Gens</strong></div>
                                <div>{personaggio.characterGens}</div>
                            </div>
                            <div>
                                <div className="w3-third"><strong>Stato Civile</strong></div>
                                <div>{"Celibe/Nubile"}</div>
                            </div>
                            {(mainContext.user.characterId == personaggio.characterId) &&
                                <div>
                                    
                                    <UserMsg id={mainContext.user.characterId} type="image" oldImg={personaggio.characterImg} setPg={setPersonaggio} />
                                </div>}
                        </div>
                        <div className="descrizione-fisica">

                            <div className="w3-row" style={{ display: 'flex', alignItems: 'center' }}>
                                <h5 >DESCRIZIONE FISICA</h5>
                                {(mainContext.user.characterId == personaggio.characterId) ? <span className="w3-margin-left">
                                    <IconButton onClick={updateDescrizionePg} component="span" classes={{ root: classes.root }}>
                                        <Icon>{modDescrizione ? 'save' : 'create'}</Icon>
                                    </IconButton>
                                </span> : null}
                            </div>
                            <section style={{ minWidth: '300px' }}>{
                                modDescrizione ?
                                    <div>

                                        <textarea className="mod-descr" value={descrizione} onChange={handleDescr} type='textarea'></textarea>
                                    </div>
                                    :
                                    <div>
                                        {parse(personaggio.description.description)}
                                    </div>
                            }
                            </section>


                        </div>
                    </div>

                    {/*curriculum and personal background column*/}
                    <div className="w3-container w3-twothird" >

                        {personaggio.characterJob !== null ?
                            <div className="cont-cv w3-container ">
                                <Avatar src={personaggio.characterJob.role_img} alt="Carica" sizes="small"></Avatar>
                                <h2>{personaggio.characterJob.roleName !== null ? personaggio.characterJob.roleName : "Nessuna Carica"}</h2>
                                {personaggio.characterJob.roleName !== null ? <IconButton onClick={openCV} component="span" classes={{ root: classes.root }}>
                                    {isOpen ? <ClearIcon /> : <ArrowDropDownOutlinedIcon />}
                                </IconButton> : null}

                            </div> : null}
                        {personaggio.characterJob.specification !== "" ? <strong>{personaggio.characterJob.specification}</strong> : null}
                        {isOpen ? <CharacterCV curriculumPg={curriculumPg} /> : null}
                        <CharacterProgress character={ personaggio} />
                        <div className="descrizione-fisica w3-rest">
                            <div className="w3-row" style={{ display: 'flex', alignItems: 'center' }}>
                                <h5 >STORIA PERSONALE</h5>
                                {mainContext.user.characterId == personaggio.characterId && (
                                    <span className="w3-margin-left">
                                        <IconButton onClick={updateStoriaPg} component="span" classes={{ root: classes.root }}>
                                            <Icon>{modStoria ? 'save' : 'create'}</Icon>
                                        </IconButton>
                                    </span>)
                                }<br></br>
                            </div>
                            {
                                modStoria ? <textarea className="text-storia" value={storia} onChange={handleStoria}
                                    type='textarea'></textarea> :
                                    <div className="storia">
                                        {parse(sanitazeHTML(personaggio.history.history))}
                                    </div>
                            }
                        </div>

                    </div>
                </div>}
        </div>
    )
} export default ProfiloPg
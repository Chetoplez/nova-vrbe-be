import React, {useEffect, useState} from 'react';
import axios from 'axios';
import '../Navbar/InfoLuogo.css';
import InfoOutlinedIcon from '@material-ui/icons/InfoOutlined';
import Moment from 'moment'
import {API_URL, getJwt} from '../../utils/api'


/*only for test */
import templum from '../../img/templum_Sp2.jpg';

/**
 * 
 * @param {idLuogo is the id of the place where you currently are} props 
 * @returns 
 */
function InfoLuogo(props) {

    const [luogo, setLuogo] = useState({});
    
    luogo.img_luogo = templum;
    const [isShow, setIsShow] = useState(false);
    var idLuogo = (props.idLuogo === undefined) ? 0 : props.idLuogo
    useEffect(() => {
        //console.log("infoLuogo", props.idLuogo)
        axios.get(API_URL.PRESENTI+"/luogo/"+idLuogo, {
            headers: {
              'Authorization': 'Fervm '+getJwt()
            }}) //check the information, description and so on
        .then(resp=>{
           // console.log(resp.data)
            setLuogo(resp.data.luogo)
        }).catch(err=>{
            //console.log("errore in infoLuogo")
            setLuogo({});
        })

        return () => {setLuogo({}); } 

    }, [props.idLuogo]);

    const showDescr = ()=>{
        setIsShow(true)
    }
    const hideDescr = ()=>{
        setIsShow(false)
    }

    return(
        <div className = 'descr-luogo w3-row w3-card'>
            <img className ='mini-luogo' src = {luogo.img_luogo} alt='mini-luogo'></img>
            <div className='meteo-luogo'>
                <div className="nome-luogo"><strong>{luogo.nomeLuogo}</strong><div onMouseEnter={showDescr} onMouseLeave={hideDescr}><InfoOutlinedIcon /></div></div>
                <div>
                    {/* <span>{luogo.data}</span>
                    <span> {luogo.temp} - {luogo.meteo}</span> */}
                    <span>{Moment(new Date()).format('DD/MM/YYYY')}</span><br></br>
                    <span> 15° - Sereno , Vento Debole</span>
                </div>
                <div className="w3-card badge-luogo w3-container" style={{display: isShow ? 'block': 'none' , position:'fixed'}}>
                    {/* <img style={{maxWidth:'100%'}} src='http://www.cassiciaco.it/navigazione/africa/siti_archeologici/douggha/images/foro_map.jpg' /> */}
                    <span style={{color:'black' , whiteSpace: 'pre-wrap'}}>{luogo.descr}</span>
                </div>
            </div>
        </div>
    );
}
export default InfoLuogo
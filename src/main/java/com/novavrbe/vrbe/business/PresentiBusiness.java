package com.novavrbe.vrbe.business;


import com.novavrbe.vrbe.dto.LuoghiDto;
import com.novavrbe.vrbe.dto.V_GuildMembers;
import com.novavrbe.vrbe.dto.V_Presenti;
import com.novavrbe.vrbe.models.presenticontroller.*;
import com.novavrbe.vrbe.repositories.impl.GuildRepositoryService;
import com.novavrbe.vrbe.repositories.impl.PresentiRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class PresentiBusiness {

    @Autowired
    private PresentiRepositoryService presentiService;

    @Autowired
    private GuildRepositoryService guildService;

    /**
     * Sposta il pg da un luogo all'altro nella lista dei presenti
     * @param request continiene le informazioni da usare per lo spostamento
     * @return true se lo spostamento è andato ok, false altrimenti
     */
    public ResponseEntity<MoveToLuogoResponse> moveToLuogo(MoveToLuogoRequest request){
        ResponseEntity<MoveToLuogoResponse> response;
        if(!StringUtils.hasText(request.getIdLuogo()) || !StringUtils.hasText(request.getCharacterId())){
            response = new ResponseEntity<>(new MoveToLuogoResponse(), HttpStatus.BAD_REQUEST);
            return response;
        }
        Integer cId = Integer.parseInt(request.getCharacterId());
        Integer idLuogo = Integer.parseInt(request.getIdLuogo());

        boolean moved =  presentiService.moveToluogo(idLuogo,cId);
        MoveToLuogoResponse res = new MoveToLuogoResponse();
        res.setMoved(moved);
        response = new ResponseEntity<>(res,HttpStatus.OK);

        return response;
    }


    public ResponseEntity<PresentiChatResponse> getPresentiChat(String chatId) {
        ResponseEntity<PresentiChatResponse> response;
        if(!StringUtils.hasText(chatId)){
            response = new ResponseEntity<>(new PresentiChatResponse(),HttpStatus.BAD_REQUEST);
            return response;
        }
        Integer idLuogo = Integer.parseInt(chatId);
        List<V_Presenti> temp = presentiService.getPresentiChat(idLuogo);
        List<Presente> presenteList = new ArrayList<>();
        for (V_Presenti presente: temp ) {
            V_GuildMembers role = guildService.getGuildMember(presente.getCharacterId());
            Presente tmp = new Presente();
            tmp.setMessaggio(presente.getMessaggio());
            tmp.setAvailable(presente.isAvailable());
            tmp.setCharacterId(presente.getCharacterId());
            tmp.setCharacterName(presente.getCharacterName());
            tmp.setCharacterSurname(presente.getCharacterSurname());

            if(role != null){
                tmp.setRole(role.getRoleName());
                tmp.setRoleImg(role.getRoleImg());
            }

            tmp.setCharacterImg(presente.getCharacterImg());
            presenteList.add(tmp);
        }
        PresentiChatResponse res = new PresentiChatResponse();
        res.setPresentiChat(presenteList);
        response = new ResponseEntity<>(res,HttpStatus.OK);

        return response;
    }

    /**
     * Torna la lista dei presenti della land, divisi per chat!
     * @return una lista di oggetti divisi per chat
     */
    public ResponseEntity<PresentiResponse> getPresenti() {
        List<ElencoPresenti> elencoPresentiList = new ArrayList<>();
        List<LuoghiDto> luoghiDtos = presentiService.getLuoghi();

        for (LuoghiDto luogo: luoghiDtos) {
            ElencoPresenti tmp = new ElencoPresenti();
            tmp.setIdLuogo(luogo.getIdLuogo());
            tmp.setNomeLuogo(luogo.getNomeLuogo());
            List<V_Presenti> presentiLuogo = presentiService.getPresentiChat(luogo.getIdLuogo());
            if(presentiLuogo.size() == 0) continue;
            for (V_Presenti pres: presentiLuogo) {
                Presente tempPresente = new Presente();
                tempPresente.setAvailable(pres.isAvailable());
                tempPresente.setCharacterId(pres.getCharacterId());
                tempPresente.setCharacterName(pres.getCharacterName());
                tempPresente.setCharacterSurname(pres.getCharacterSurname());
                tempPresente.setMessaggio(pres.getMessaggio());
                tmp.getPresenteList().add(tempPresente);
            }
            elencoPresentiList.add(tmp);
        }

        PresentiResponse res= new PresentiResponse();
        res.setPresenti(elencoPresentiList);
        return new ResponseEntity<>(res,HttpStatus.OK);

    }

    /**
     * Aggiorna il messaggio personale mostrato nella lista dei presenti, sia della chat che della mappa.
     * NOTA: si può anche aggiungere un messaggio vuoto.
     * @param request Nella request vi è il characterId e il nuovo messaggio da inserire
     * @return true se l'aggiornamento è andato bene, false altrimenti
     */
    public ResponseEntity<UpdateMessageResponse> updateOnlineMessage(UpdateMessageRequest request) {
        ResponseEntity<UpdateMessageResponse> response;
        if(!StringUtils.hasText(request.getCharacterId())){
            response = new ResponseEntity<>(new UpdateMessageResponse(), HttpStatus.BAD_REQUEST);
            return response;
        }
        Integer chId = Integer.parseInt(request.getCharacterId());
        boolean updated = presentiService.changePersonalMessage(chId,request.getMessage());
        UpdateMessageResponse res = new UpdateMessageResponse();
        res.setUpdated(updated);
        response = new ResponseEntity<>(res,HttpStatus.OK);
        return response;
    }

    /**
     * Aggiorna lo stato Online di un character
     * @param request richiesta con lo stato online e il characterdID
     * @return true se è andato bene, false altrimenti
     */
    public ResponseEntity<UpdateMessageResponse> updateAvailability(UpdateAvailabilityRequest request) {
        ResponseEntity<UpdateMessageResponse> response;
        if(!StringUtils.hasText(request.getCharacterId()) || !StringUtils.hasText(request.getAvailable()) ){
            response = new ResponseEntity<>(new UpdateMessageResponse(), HttpStatus.BAD_REQUEST);
            return response;
        }
        Integer chId = Integer.parseInt(request.getCharacterId());
        boolean available = request.getAvailable() == "true";
        boolean updated = presentiService.changeAvailability(chId, available);
        UpdateMessageResponse res = new UpdateMessageResponse();
        res.setUpdated(updated);
        response = new ResponseEntity<>(res,HttpStatus.OK);
        return response;
    }

    public ResponseEntity<GetLuogoResponse> getInfoLuogo(String idLuogo) {
        ResponseEntity<GetLuogoResponse> response ;
        if(!StringUtils.hasText(idLuogo)){
            response = new ResponseEntity<>(new GetLuogoResponse(),HttpStatus.BAD_REQUEST);
            return response;
        }
        Integer idLoc = Integer.parseInt(idLuogo);
        LuoghiDto dto= presentiService.getInfoLuogo(idLoc);
        if(dto == null){
            response = new ResponseEntity<>(new GetLuogoResponse(),HttpStatus.BAD_REQUEST);
            return response;
        }
        InfoLuogo infoLuogo = new InfoLuogo();
        infoLuogo.setIdLuogo(dto.getIdLuogo());
        infoLuogo.setNomeLuogo(dto.getNomeLuogo());
        infoLuogo.setStatoLuogo(dto.getStatoLuogo());
        infoLuogo.setDescr(dto.getDescr());
        infoLuogo.setImmagine(dto.getImmagine());

        GetLuogoResponse res = new GetLuogoResponse();
        res.setLuogo(infoLuogo);
        response = new ResponseEntity<>(res,HttpStatus.OK);
        return response;
    }

    /**
     * Metodo che , al login viene chiamato per passare allo stato online.
     * @param request in questo payload ci va l'id del pg e il suo stato che sarò true, al login e false al logout
     * @return true, se l'operazione è andata a buon fine, false altrimenti
     */
    public ResponseEntity<GetOnlineResponse> getOnline(GetOnlineRequest request) {
        ResponseEntity<GetOnlineResponse> response ;
        if(!StringUtils.hasText(request.getCharacterId())){
            response = new ResponseEntity<>(new GetOnlineResponse(), HttpStatus.BAD_REQUEST);
            return response;
        }
        Integer chId = Integer.parseInt(request.getCharacterId());

        boolean changed = presentiService.changeOnline(chId,request.isOnline());
        GetOnlineResponse res = new GetOnlineResponse();
        res.setChanged(changed);
        response = new ResponseEntity<>(res,HttpStatus.OK);
        return  response;
    }
}

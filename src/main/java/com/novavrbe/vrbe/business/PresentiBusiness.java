package com.novavrbe.vrbe.business;


import com.novavrbe.vrbe.models.presenticontroller.*;
import com.novavrbe.vrbe.repositories.impl.PresentiRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class PresentiBusiness {

    @Autowired
    PresentiRepositoryService presentiService;

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
        //TODO
        return null;
    }

    public ResponseEntity<PresentiResponse> getPresenti() {
        //TODO
        return null;
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

    public ResponseEntity<UpdateMessageResponse> updateAvailability(UpdateAvailabilityRequest request) {
        ResponseEntity<UpdateMessageResponse> response;
        if(!StringUtils.hasText(request.getCharacterId()) || !StringUtils.hasText(request.getAvailable()) ){
            response = new ResponseEntity<>(new UpdateMessageResponse(), HttpStatus.BAD_REQUEST);
            return response;
        }
        Integer chId = Integer.parseInt(request.getCharacterId());
        boolean available = (request.getAvailable() == "true") ? true : false;
        boolean updated = presentiService.changeAvailability(chId, available);
        UpdateMessageResponse res = new UpdateMessageResponse();
        res.setUpdated(updated);
        response = new ResponseEntity<>(res,HttpStatus.OK);
        return response;
    }
}

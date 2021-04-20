package com.novavrbe.vrbe.business;


import com.novavrbe.vrbe.models.presenticontroller.MoveToLuogoRequest;
import com.novavrbe.vrbe.models.presenticontroller.MoveToLuogoResponse;
import com.novavrbe.vrbe.models.presenticontroller.PresentiChatResponse;
import com.novavrbe.vrbe.models.presenticontroller.PresentiResponse;
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
}

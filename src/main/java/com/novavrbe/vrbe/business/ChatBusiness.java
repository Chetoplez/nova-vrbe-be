package com.novavrbe.vrbe.business;

import com.novavrbe.vrbe.models.chatcontroller.AddMessageRequest;
import com.novavrbe.vrbe.models.chatcontroller.AddMessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ChatBusiness {

    public ResponseEntity<AddMessageResponse> addMessage(AddMessageRequest request){
        ResponseEntity<AddMessageResponse> response = null;

        if(!StringUtils.hasText(request.getChatId()) || request.getChatMessage() == null){
            response = new ResponseEntity<>(new AddMessageResponse(), HttpStatus.BAD_REQUEST);
            return response;
        }



        return response;
    }

}

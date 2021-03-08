package com.novavrbe.vrbe.controllers;

import com.novavrbe.vrbe.business.ChatBusiness;
import com.novavrbe.vrbe.models.chatcontroller.AddMessageRequest;
import com.novavrbe.vrbe.models.chatcontroller.AddMessageResponse;
import com.novavrbe.vrbe.models.chatcontroller.DeleteMessageRequest;
import com.novavrbe.vrbe.models.chatcontroller.DeleteMessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatBusiness chatBusiness;

    @PostMapping("/addmessage")
    public ResponseEntity<AddMessageResponse> addMessage(@RequestBody AddMessageRequest request){
        return chatBusiness.addMessage(request);
    }

    @DeleteMapping("/deleteMessage")
    public ResponseEntity<DeleteMessageResponse> deleteMessage(DeleteMessageRequest request){
        return  chatBusiness.deleteMessage(request);
    }

}

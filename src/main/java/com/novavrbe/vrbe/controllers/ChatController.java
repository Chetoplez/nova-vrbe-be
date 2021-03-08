package com.novavrbe.vrbe.controllers;

import com.novavrbe.vrbe.business.ChatBusiness;
import com.novavrbe.vrbe.models.chatcontroller.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatBusiness chatBusiness;

    @PutMapping("/addmessage")
    public ResponseEntity<AddMessageResponse> addMessage(@RequestBody AddMessageRequest request){
        return chatBusiness.addMessage(request);
    }

    @GetMapping("/id={id}&timeWindow={window}")
    public ResponseEntity<GetChatResponse> getChatById(@PathVariable String id, @PathVariable String window){
        return chatBusiness.getChatById(id,window);
    }

    @DeleteMapping("/deletemessage")
    public ResponseEntity<DeleteMessageResponse> deleteMessage(DeleteMessageRequest request){
        return  chatBusiness.deleteMessage(request);
    }

}

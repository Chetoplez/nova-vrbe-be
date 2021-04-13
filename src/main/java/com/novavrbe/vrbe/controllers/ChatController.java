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

    @PostMapping("/addmessage")
    public ResponseEntity<AddMessageResponse> addMessage(@RequestBody AddMessageRequest request){
        System.out.println("passo da qui, infame: "+request.getChatId());
        System.out.println("Vediamo il timpestamp di merda:" +request.getChatMessage().getTimestamp());
        return chatBusiness.addMessage(request);
    }

    @GetMapping("/id={id}&timeWindow={window}")
    public ResponseEntity<GetChatResponse> getChatById(@PathVariable String id, @PathVariable String window){
        System.out.println("Cazzo di merda");
        return chatBusiness.getChatById(id,window);
    }

    @DeleteMapping("/deletemessage")
    public ResponseEntity<DeleteMessageResponse> deleteMessage(DeleteMessageRequest request){
        return  chatBusiness.deleteMessage(request);
    }

    @GetMapping("/ischatupdate/chatId={chatId}&timestamp={tms}")
    public ResponseEntity<IsChatUpdatedResponse> isChatUpdate(@PathVariable String chatId, @PathVariable String tms){
        return chatBusiness.isChatUpdated(chatId,tms);
    }

}

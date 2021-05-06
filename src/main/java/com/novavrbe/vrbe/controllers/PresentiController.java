package com.novavrbe.vrbe.controllers;


import com.novavrbe.vrbe.business.PresentiBusiness;
import com.novavrbe.vrbe.models.presenticontroller.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/presenti")
public class PresentiController {
    @Autowired
    PresentiBusiness presentiBusiness;

    @GetMapping("/luogo/{idLuogo}")
    ResponseEntity<GetLuogoResponse> getLuogo(@PathVariable String idLuogo){
        return presentiBusiness.getInfoLuogo(idLuogo);
    }

    @GetMapping("/presenti")
    ResponseEntity<PresentiResponse> getPresenti(){
        return presentiBusiness.getPresenti();

    }

    @GetMapping("/presenti/chatId={chatId}")
    public ResponseEntity<PresentiChatResponse> getPresentiChat(@PathVariable String chatId){
        return presentiBusiness.getPresentiChat(chatId);
    }

    @PatchMapping("/moveto")
    public ResponseEntity<MoveToLuogoResponse> moveToLuogo(@RequestBody MoveToLuogoRequest request){
        return presentiBusiness.moveToLuogo(request);
    }

    @PatchMapping("/updatemessage")
    public ResponseEntity<UpdateMessageResponse> updateMessage(@RequestBody UpdateMessageRequest request){
        return presentiBusiness.updateOnlineMessage(request);
    }

    @PatchMapping("/updateavailable")
    public ResponseEntity<UpdateMessageResponse> updateAvailability(@RequestBody UpdateAvailabilityRequest request){
        return presentiBusiness.updateAvailability(request);
    }
}

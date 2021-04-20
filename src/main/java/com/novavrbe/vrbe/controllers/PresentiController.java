package com.novavrbe.vrbe.controllers;


import com.novavrbe.vrbe.business.PresentiBusiness;
import com.novavrbe.vrbe.models.presenticontroller.MoveToLuogoRequest;
import com.novavrbe.vrbe.models.presenticontroller.MoveToLuogoResponse;
import com.novavrbe.vrbe.models.presenticontroller.PresentiChatResponse;
import com.novavrbe.vrbe.models.presenticontroller.PresentiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/presenti")
public class PresentiController {
    @Autowired
    PresentiBusiness presentiBusiness;

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
}

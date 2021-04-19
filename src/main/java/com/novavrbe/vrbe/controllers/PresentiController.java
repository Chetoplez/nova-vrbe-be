package com.novavrbe.vrbe.controllers;


import com.novavrbe.vrbe.business.PresentiBusiness;
import com.novavrbe.vrbe.models.presenticontroller.PresentiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/presenti")
public class PresentiController {
    @Autowired
    PresentiBusiness presentiBusiness;

    @GetMapping("/presenti")
    ResponseEntity<PresentiResponse> getPresenti(){
        return presentiBusiness.getPresenti();

    }
}

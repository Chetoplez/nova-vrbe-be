package com.novavrbe.vrbe.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/vrbe")
public class VrbeController {

    @GetMapping("/isalive")
    public ResponseEntity<Boolean> isAlive(){
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

}

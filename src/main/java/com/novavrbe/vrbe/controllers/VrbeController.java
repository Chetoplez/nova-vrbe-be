package com.novavrbe.vrbe.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/vrbe")
public class VrbeController {

    /*
    * Questo è il mio primo test di commit!
    * */
    @GetMapping("/isalive")
    public String isAlive(){
        return "im alive";
    }
}

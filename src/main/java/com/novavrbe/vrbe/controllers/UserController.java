package com.novavrbe.vrbe.controllers;

import com.novavrbe.vrbe.business.UserBusiness;
import com.novavrbe.vrbe.models.AddUserRequest;
import com.novavrbe.vrbe.models.charactermodels.GenericUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserBusiness userBusiness;

    @PutMapping("/create")
    public ResponseEntity<GenericUser> createUser(@RequestBody AddUserRequest addUserRequest){
        return userBusiness.createUser(addUserRequest);
    }

    public void login(){

    }

    public void logout(){

    }

    public void getUser(){

    }

    public void getInventory(){

    }

    public void updateInventory(){

    }

}

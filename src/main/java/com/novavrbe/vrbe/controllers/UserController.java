package com.novavrbe.vrbe.controllers;

import com.novavrbe.vrbe.business.UserBusiness;
import com.novavrbe.vrbe.models.*;
import com.novavrbe.vrbe.models.charactermodels.GenericUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserBusiness userBusiness;

    @PutMapping("/create")
    public ResponseEntity<GenericUser> createUser(@RequestBody AddUserRequest addUserRequest){
        return userBusiness.createUser(addUserRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest login){
        return userBusiness.login(login);
    }

    @PostMapping("/logout")
    public ResponseEntity<LogoutResponse> logout(@RequestBody LogoutRequest logoutRequest){
        return userBusiness.logout(logoutRequest);
    }

    @GetMapping("getuser")
    public ResponseEntity<GetUserResponse> getUser(@RequestParam String characterId){
        return userBusiness.getUser(characterId);
    }

    @GetMapping("getinventory")
    public ResponseEntity<GetInventoryResponse> getInventory(@RequestParam String characterId){
        return userBusiness.getInventory(characterId);
    }

    @PostMapping("updateinventory")
    public ResponseEntity<UpdateInventoryResponse> updateInventory(@RequestBody UpdateInventoryRequest updateInventoryRequest){
        return userBusiness.updateInventory(updateInventoryRequest);
    }

}

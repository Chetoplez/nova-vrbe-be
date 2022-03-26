package com.novavrbe.vrbe.controllers;

import com.novavrbe.vrbe.business.UserBusiness;
import com.novavrbe.vrbe.dto.GenericUserDto;
import com.novavrbe.vrbe.models.usercontroller.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserBusiness userBusiness;


    @PutMapping("/create")
    public ResponseEntity<GenericUserDto> createUser(@RequestBody AddUserRequest addUserRequest){
        return userBusiness.createUser(addUserRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest login) throws Exception {
        return userBusiness.login(login);
    }

    @PostMapping("/logout")
    public ResponseEntity<LogoutResponse> logout(@RequestBody LogoutRequest logoutRequest){
        return userBusiness.logout(logoutRequest);
    }

    @GetMapping("getuser/{userId}")
    public ResponseEntity<GetUserResponse> getUser(@PathVariable String userId){
        return userBusiness.getUser(userId);
    }

    @PostMapping("/checkmail")
    public ResponseEntity<CheckEmailResponse> checkEmail(@RequestBody CheckEmailRequest request){
        return userBusiness.checkEmailAddress(request);
    }

}

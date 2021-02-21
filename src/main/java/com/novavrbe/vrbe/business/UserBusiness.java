package com.novavrbe.vrbe.business;

import com.novavrbe.vrbe.models.*;
import com.novavrbe.vrbe.models.charactermodels.GenericUser;
import com.novavrbe.vrbe.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserBusiness {

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<GenericUser> createUser(AddUserRequest addUserRequest){

        return null;
    }

    public ResponseEntity<LoginResponse> login(LoginRequest loginRequest){

        return null;
    }

    public ResponseEntity<LogoutResponse> logout(LogoutRequest logoutRequest){

        return null;
    }

    public ResponseEntity<GetUserResponse> getUser(String characterId){
        ArrayList<GenericUser> user = (ArrayList<GenericUser>) userRepository.findAll();
        if(user != null){

        }

        return null;
    }

    public ResponseEntity<GetInventoryResponse> getInventory(String characterId){
        return null;
    }

    public ResponseEntity<UpdateInventoryResponse> updateInventory(UpdateInventoryRequest request){
        return null;
    }

}

package com.novavrbe.vrbe.business;

import com.novavrbe.vrbe.models.*;
import com.novavrbe.vrbe.models.charactermodels.GenericUser;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserBusiness {

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

        return null;
    }

    public ResponseEntity<GetInventoryResponse> getInventory(String characterId){
        return null;
    }

    public ResponseEntity<UpdateInventoryResponse> updateInventory(UpdateInventoryRequest request){
        return null;
    }

}

package com.novavrbe.vrbe.business;

import com.novavrbe.vrbe.models.*;
import com.novavrbe.vrbe.models.charactermodels.GenericUser;
import com.novavrbe.vrbe.models.charactermodels.UserPojo;
import com.novavrbe.vrbe.repositories.GuildRepository;
import com.novavrbe.vrbe.repositories.UserRepository;
import com.novavrbe.vrbe.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;

@Service
public class UserBusiness {

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<GenericUser> createUser(AddUserRequest addUserRequest){
        ResponseEntity<GenericUser> response = null;
        UserPojo user = addUserRequest.getUserPojo();

        if(!CollectionUtils.isEmpty(userRepository.findUsersByEmail(user.getEmail()))){
            response = new ResponseEntity<GenericUser>(new GenericUser(), HttpStatus.BAD_REQUEST);
            return response;
        }

        GenericUser genericUser = UserUtils.createGenericUser(user.getName(), user.getLastname(), user.getBirthday(), user.getGender(), user.getEmail(), user.getPassword(), user.getNickname());
        //Remove private data
        genericUser.setComposedsecret("");
        genericUser.setSalt("");

        if(userRepository.save(genericUser) != null){
            response = new ResponseEntity<GenericUser>(genericUser, HttpStatus.OK);
        }else{
            response = new ResponseEntity<GenericUser>(new GenericUser(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }

    public ResponseEntity<LoginResponse> login(LoginRequest loginRequest){

        return null;
    }

    public ResponseEntity<LogoutResponse> logout(LogoutRequest logoutRequest){

        return null;
    }

    public ResponseEntity<GetUserResponse> getUser(String characterId){
        ArrayList<GenericUser> user = (ArrayList<GenericUser>) userRepository.findAll();


        return null;
    }

    public ResponseEntity<GetInventoryResponse> getInventory(String characterId){
        return null;
    }

    public ResponseEntity<UpdateInventoryResponse> updateInventory(UpdateInventoryRequest request){
        return null;
    }

}

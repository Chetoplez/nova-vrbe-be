package com.novavrbe.vrbe.business;

import com.novavrbe.vrbe.models.*;
import com.novavrbe.vrbe.models.charactermodels.GenericUser;
import com.novavrbe.vrbe.models.charactermodels.UserPojo;
import com.novavrbe.vrbe.repositories.UserRepository;
import com.novavrbe.vrbe.utils.LoginUtils;
import com.novavrbe.vrbe.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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

        if(userRepository.save(genericUser) != null){
            UserUtils.cleanUserSensitiveData(genericUser);
            response = new ResponseEntity<GenericUser>(genericUser, HttpStatus.OK);
        }else{
            response = new ResponseEntity<GenericUser>(new GenericUser(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }

    public ResponseEntity<LoginResponse> login(LoginRequest loginRequest){
        ResponseEntity<LoginResponse> response = null;

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setSuccess(false);

        if(loginRequest != null && StringUtils.hasText(loginRequest.getEmail()) && StringUtils.hasText(loginRequest.getPsw())){
            List<GenericUser> users = userRepository.findUsersByEmail(loginRequest.getEmail());
            if(!CollectionUtils.isEmpty(users)){
                GenericUser user = users.get(0);
                loginResponse.setSuccess(LoginUtils.canLogin(loginRequest.getPsw(), user));
            }
        }

        response = new ResponseEntity<>(loginResponse, HttpStatus.OK);

        return response;
    }

    public ResponseEntity<LogoutResponse> logout(LogoutRequest logoutRequest){

        return null;
    }

    public ResponseEntity<GetUserResponse> getUser(String characterId){
        ResponseEntity<GetUserResponse> response = null;
        if(!StringUtils.hasText(characterId)){
            response = new ResponseEntity<GetUserResponse>(new GetUserResponse(), HttpStatus.BAD_REQUEST);
            return response;
        }

        BigDecimal id = new BigDecimal(characterId);
        Optional<GenericUser> user = userRepository.findById(id);

        if(user != null){
            GetUserResponse getUserResponse = new GetUserResponse();
            getUserResponse.setUser(user.get());
            response = new ResponseEntity<GetUserResponse>(getUserResponse, HttpStatus.BAD_REQUEST);
        }

        return response;
    }

}

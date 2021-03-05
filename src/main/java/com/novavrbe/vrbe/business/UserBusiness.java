package com.novavrbe.vrbe.business;

import com.novavrbe.vrbe.dto.GenericUserDto;
import com.novavrbe.vrbe.models.charactermodels.UserDtoPojo;
import com.novavrbe.vrbe.models.usercontroller.*;
import com.novavrbe.vrbe.repositories.impl.UserRepositoryService;
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

@Service
public class UserBusiness {

    @Autowired
    private UserRepositoryService userRepositoryService;

    public ResponseEntity<GenericUserDto> createUser(AddUserRequest addUserRequest){
        ResponseEntity<GenericUserDto> response = null;
        UserDtoPojo user = addUserRequest.getUserPojo();

        if(!CollectionUtils.isEmpty(userRepositoryService.findUsersByEmail(user.getEmail()))){
            response = new ResponseEntity<GenericUserDto>(new GenericUserDto(), HttpStatus.BAD_REQUEST);
            return response;
        }

        GenericUserDto genericUserDto = UserUtils.createGenericUser(user.getName(), user.getLastname(), user.getBirthday(), user.getGender(), user.getEmail(), user.getPassword(), user.getNickname());

        if(userRepositoryService.saveUser(genericUserDto) != null){
            UserUtils.cleanUserSensitiveData(genericUserDto);
            response = new ResponseEntity<GenericUserDto>(genericUserDto, HttpStatus.OK);
        }else{
            response = new ResponseEntity<GenericUserDto>(new GenericUserDto(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }

    public ResponseEntity<LoginResponse> login(LoginRequest loginRequest){
        ResponseEntity<LoginResponse> response = null;

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setSuccess(false);

        if(loginRequest != null && StringUtils.hasText(loginRequest.getEmail()) && StringUtils.hasText(loginRequest.getPsw())){
            List<GenericUserDto> users = userRepositoryService.findUsersByEmail(loginRequest.getEmail());
            if(!CollectionUtils.isEmpty(users)){
                GenericUserDto user = users.get(0);
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
        GenericUserDto user = userRepositoryService.findUsersById(id);

        if(user != null){
            GetUserResponse getUserResponse = new GetUserResponse();
            getUserResponse.setUser(user);
            response = new ResponseEntity<GetUserResponse>(getUserResponse, HttpStatus.BAD_REQUEST);
        }

        return response;
    }

}

package com.novavrbe.vrbe.business;

import com.novavrbe.vrbe.dto.GenericUserDto;
import com.novavrbe.vrbe.models.charactermodels.UserDtoPojo;
import com.novavrbe.vrbe.models.usercontroller.*;
import com.novavrbe.vrbe.repositories.impl.UserRepositoryService;
import com.novavrbe.vrbe.utils.JwtUtils;
import com.novavrbe.vrbe.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class UserBusiness {

    @Autowired
    private UserRepositoryService userRepositoryService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    JwtUtils jwtUtils;

    public ResponseEntity<GenericUserDto> createUser(AddUserRequest addUserRequest){
        ResponseEntity<GenericUserDto> response = null;
        UserDtoPojo user = addUserRequest.getUserPojo();

        if(!CollectionUtils.isEmpty(userRepositoryService.findUsersByEmail(user.getEmail()))){
            response = new ResponseEntity<GenericUserDto>(new GenericUserDto(), HttpStatus.BAD_REQUEST);
            return response;
        }

        GenericUserDto genericUserDto = UserUtils.createGenericUser( passwordEncoder, user.getEmail(), user.getPassword(), user.getNickname());

        if(userRepositoryService.saveUser(genericUserDto) != null){
            UserUtils.cleanUserSensitiveData(genericUserDto);
            response = new ResponseEntity<GenericUserDto>(genericUserDto, HttpStatus.OK);
        }else{
            response = new ResponseEntity<GenericUserDto>(new GenericUserDto(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }

    public ResponseEntity<LoginResponse> login(LoginRequest loginRequest) throws Exception {
        ResponseEntity<LoginResponse> response = null;

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setSuccess(false);


        try {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPsw())
        );
        }catch (BadCredentialsException e){
            response = new ResponseEntity<>(loginResponse,HttpStatus.OK);
            return response;
        }
        GenericUserDto dto = userRepositoryService.loadUserByUsername(loginRequest.getEmail());
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        String strDate = sdfDate.format(now);
        Date lastLogin = sdfDate.parse(strDate);

        dto.setLastlogin(lastLogin.getTime());
        userRepositoryService.saveUser(dto);
        loginResponse.setSuccess(true);
        loginResponse.setRole(dto.getRole());
        loginResponse.setUserId(dto.getId());

        final String jwt = jwtUtils.generateToken(dto);
        loginResponse.setJwt(jwt);


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

    /**
     * Verifica che l'email che sta inserendo l'utente sia presente o meno, così da avviare la registrazione o il login
     * @param request
     * @return
     */
    public ResponseEntity<CheckEmailResponse> checkEmailAddress(CheckEmailRequest request) {
        ResponseEntity<CheckEmailResponse> response;
        CheckEmailResponse emailResponse = new CheckEmailResponse();
        emailResponse.setPresente(false);
        if(!StringUtils.hasText(request.getEmail())){
            response = new ResponseEntity<>(emailResponse, HttpStatus.BAD_REQUEST);
            return response;
        }
        List<GenericUserDto> users = userRepositoryService.findUsersByEmail(request.getEmail());
        emailResponse.setPresente(!CollectionUtils.isEmpty(users) );
        response = new ResponseEntity<>(emailResponse, HttpStatus.OK);
        return response;
    }
}

package com.novavrbe.vrbe.repositories.impl;

import com.novavrbe.vrbe.dto.GenericUserDto;
import com.novavrbe.vrbe.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class UserRepositoryService {

    @Autowired
    private UserRepository userRepository;

    public List<GenericUserDto> findUsersByEmail(String email){
        return userRepository.findUsersByEmail(email);
    }

    public GenericUserDto saveUser(GenericUserDto user){
        return userRepository.save(user);
    }

    public GenericUserDto findUsersById(BigDecimal id){
        GenericUserDto user = null;
        Optional<GenericUserDto> dto = userRepository.findById(id);
        user = dto != null && dto.get() != null ? dto.get() : null;

        return user;
    }


}

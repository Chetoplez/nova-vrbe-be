package com.novavrbe.vrbe.repositories.impl;

import com.novavrbe.vrbe.models.charactermodels.GenericUser;
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

    public List<GenericUser> findUsersByEmail(String email){
        return userRepository.findUsersByEmail(email);
    }

    public GenericUser saveUser(GenericUser user){
        return userRepository.save(user);
    }

    public GenericUser findUsersById(BigDecimal id){
        GenericUser user = null;
        Optional<GenericUser> dto = userRepository.findById(id);
        user = dto != null && dto.get() != null ? dto.get() : null;

        return user;
    }


}

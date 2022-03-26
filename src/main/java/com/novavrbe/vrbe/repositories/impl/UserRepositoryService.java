package com.novavrbe.vrbe.repositories.impl;

import com.novavrbe.vrbe.dto.GenericUserDto;
import com.novavrbe.vrbe.models.usercontroller.GameUserDetails;
import com.novavrbe.vrbe.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class UserRepositoryService implements UserDetailsService {

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
        user = dto.isPresent() ? dto.get() : null;

        return user;
    }

    public GameUserDetails retrieveUser(String characterId){
        GameUserDetails user = null;

        GenericUserDto userDto = findUsersById(new BigDecimal(characterId));
        if(userDto != null){
            user = new GameUserDetails();
        }

        return user;
    }


    @Override
    public GenericUserDto loadUserByUsername(String userName) throws UsernameNotFoundException {
        List<GenericUserDto> dtoList = findUsersByEmail(userName);
        return dtoList.get(0);
    }


}

package com.novavrbe.vrbe.repositories.impl;

import com.novavrbe.vrbe.dto.LuoghiDto;
import com.novavrbe.vrbe.repositories.LuoghiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LuoghiRepositoryService {

    @Autowired
    LuoghiRepository luoghiRepository;

    public LuoghiDto getLuogoById(Integer idLuogo) {
        Optional<LuoghiDto> opt = luoghiRepository.findById(idLuogo);
        return opt.get();
    }
}

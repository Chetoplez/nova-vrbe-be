package com.novavrbe.vrbe.repositories.impl;

import com.novavrbe.vrbe.dto.MissivaDto;
import com.novavrbe.vrbe.repositories.MissiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MissiveRepositoryService {

    @Autowired
    private MissiveRepository missiveRepository;

    public List<MissivaDto> getInbox(Integer chId) {
        List<MissivaDto> missivaDtos = missiveRepository.findByChToAndDeletedFalse(chId.toString());
        if(missivaDtos == null)
            return new ArrayList<>();
        else
            return missivaDtos;
    }
}

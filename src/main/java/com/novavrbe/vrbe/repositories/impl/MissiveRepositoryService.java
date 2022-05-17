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
        List<MissivaDto> missivaDtos = missiveRepository.findByChToAndDeletedToFalse(chId.toString());
        if(missivaDtos == null)
            return new ArrayList<>();
        else
            return missivaDtos;
    }

    public List<MissivaDto> getSentBox(Integer chId) {
        List<MissivaDto> missivaDtos = missiveRepository.findByChFromAndDeletedFromFalse(chId);
        if(missivaDtos == null)
            return new ArrayList<>();
        else
            return missivaDtos;
    }

    public MissivaDto sendMissiva(MissivaDto dto) {
        return  missiveRepository.save(dto);

    }

    public ArrayList<MissivaDto> checkNewMail(Integer chId) {
        ArrayList<MissivaDto> missivaDtos = missiveRepository.findByChToAndDeletedToFalseAndIsReadFalse(chId.toString());
        if(missivaDtos == null)
            return new ArrayList<>();
        else
            return missivaDtos;
    }

    public ArrayList<MissivaDto> getMissiveList(ArrayList<Integer> array, String chId ) {
        return (ArrayList<MissivaDto>) missiveRepository.findAllById(array);
    }

    public void saveAll(ArrayList<MissivaDto> toReadDto) {
        missiveRepository.saveAll(toReadDto);
    }
}

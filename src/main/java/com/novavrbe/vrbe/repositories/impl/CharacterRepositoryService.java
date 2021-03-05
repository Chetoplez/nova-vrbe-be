package com.novavrbe.vrbe.repositories.impl;

import com.novavrbe.vrbe.dto.CharacterDescriptionDto;
import com.novavrbe.vrbe.dto.CharacterDto;
import com.novavrbe.vrbe.dto.CharacterHistoryDto;
import com.novavrbe.vrbe.dto.CharacterStatisticsDto;
import com.novavrbe.vrbe.repositories.CharacterDescriptionRepository;
import com.novavrbe.vrbe.repositories.CharacterHistoryRepository;
import com.novavrbe.vrbe.repositories.CharacterRepository;
import com.novavrbe.vrbe.repositories.CharacterStatisticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CharacterRepositoryService {

    @Autowired
    private CharacterRepository characterRepository;
    @Autowired
    private CharacterHistoryRepository characterHistoryRepository;
    @Autowired
    private CharacterDescriptionRepository characterDescriptionRepository;
    @Autowired
    private CharacterStatisticRepository characterStatisticRepository;

    public CharacterDto retrieveCharacterFromId(Integer characterId){
        CharacterDto characterDto = null;
        if(characterId != null){
            Optional<CharacterDto> dto = characterRepository.findById(characterId);
            characterDto = dto != null && dto.get() != null ? dto.get() : null;
        }

        return characterDto;
    }

    public CharacterHistoryDto retrieveCharacterHistory(Integer historyId){
        CharacterHistoryDto historyDto = null;
        if(historyId != null){
            Optional<CharacterHistoryDto> dto = characterHistoryRepository.findById(historyId);
            historyDto = dto != null && dto.get() != null ? dto.get() : null;
        }
        return historyDto;
    }

    public CharacterDescriptionDto retrieveCharacterDescription(Integer descriptionId){
        CharacterDescriptionDto descriptionDto = null;
        if(descriptionId != null){
            Optional<CharacterDescriptionDto> dto = characterDescriptionRepository.findById(descriptionId);
            descriptionDto = dto != null && dto.get() != null ? dto.get() : null;
        }

        return descriptionDto;
    }

    public CharacterStatisticsDto retrieveCharacterStatistics(Integer characterId){
        CharacterStatisticsDto characterStatisticsDto = null;
        if(characterId != null){
            Optional<CharacterStatisticsDto> dto = characterStatisticRepository.findById(characterId);
            characterStatisticsDto = dto != null && dto.get() != null ? dto.get() : null;
        }

        return characterStatisticsDto;
    }


}

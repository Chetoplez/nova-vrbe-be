package com.novavrbe.vrbe.repositories.impl;

import com.novavrbe.vrbe.dto.*;
import com.novavrbe.vrbe.models.charactermodels.Character;
import com.novavrbe.vrbe.repositories.*;
import com.novavrbe.vrbe.utils.CharacterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
    @Autowired
    private CharacterTemporaryEffectsRepository characterTemporaryEffectsRepository;

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

    public List<CharacterTemporaryEffectDto> retrieveCharacterTemporaryEffects(Integer characterId){
        List<CharacterTemporaryEffectDto> effects = new ArrayList<>();

        if(characterId != null){
            effects = characterTemporaryEffectsRepository.findTemporaryEffectForUser(characterId);
        }

        return effects;
    }

    public boolean saveNewCharacter(Integer userId, Character character){
        boolean saved = true;

        CharacterDto characterDto = CharacterUtils.buildCharacterDtoFromCharacter(userId, character);

        if(characterDto != null){
            saved = characterRepository.save(characterDto) != null;
            if(saved){
                CharacterHistoryDto historyDto = CharacterUtils.buildCharacterHistoryForDto(userId, character);
                if(historyDto != null){
                    characterHistoryRepository.save(historyDto);
                }

                CharacterDescriptionDto characterDescriptionDto = CharacterUtils.buildCharacterDescriptionForDto(userId, character);
                if(characterDescriptionDto != null){
                    characterDescriptionRepository.save(characterDescriptionDto);
                }

                CharacterStatisticsDto characterStatisticsDto = CharacterUtils.buildCharacterStatisticForDto(userId, character);
                if(characterStatisticsDto != null){
                    characterStatisticRepository.save(characterStatisticsDto);
                }

            }
        }

        return saved;
    }


}

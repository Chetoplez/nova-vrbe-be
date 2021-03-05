package com.novavrbe.vrbe.business;

import com.novavrbe.vrbe.dto.CharacterDescriptionDto;
import com.novavrbe.vrbe.dto.CharacterDto;
import com.novavrbe.vrbe.dto.CharacterHistoryDto;
import com.novavrbe.vrbe.dto.CharacterStatisticsDto;
import com.novavrbe.vrbe.models.charactercontroller.*;
import com.novavrbe.vrbe.models.charactermodels.Character;
import com.novavrbe.vrbe.repositories.CharacterDescriptionRepository;
import com.novavrbe.vrbe.repositories.CharacterHistoryRepository;
import com.novavrbe.vrbe.repositories.CharacterRepository;
import com.novavrbe.vrbe.repositories.CharacterStatisticRepository;
import com.novavrbe.vrbe.utils.CharacterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class CharacterBusiness {

    @Autowired
    private CharacterRepository characterRepository;
    @Autowired
    private CharacterHistoryRepository characterHistoryRepository;
    @Autowired
    private CharacterDescriptionRepository characterDescriptionRepository;
    @Autowired
    private CharacterStatisticRepository characterStatisticRepository;

    public ResponseEntity<AddCharacterResponse> addCharacter(AddCharacterRequest addCharacterRequest){

        return null;
    }

    public ResponseEntity<GetCharacterResponse> getCharacter(String characterId){
        ResponseEntity<GetCharacterResponse> response = null;

        if(!StringUtils.hasText(characterId)){
            response = new ResponseEntity<GetCharacterResponse>(new GetCharacterResponse(), HttpStatus.BAD_REQUEST);
            return response;
        }

        Integer cID = Integer.parseInt(characterId);

        Optional<CharacterDto> characterDto = characterRepository.findById(cID);
        if(characterDto != null && characterDto.get() != null){
            GetCharacterResponse getCharacterResponse = new GetCharacterResponse();

            Character character = new Character();
            CharacterUtils.fillCharacterFieldsFromDto(character, characterDto.get());
            CharacterUtils.fillCharacterHistoryFromDto(character, retrieveCharacterHistory(cID));
            CharacterUtils.fillCharacterDescriptionFromDto(character, retrieveCharacterDescription(cID));
            CharacterUtils.fillCharacterStatisticsFromDto(character, retrieveCharacterStatistics(cID));

            getCharacterResponse.setCharacter(character);
            response = new ResponseEntity<GetCharacterResponse>(getCharacterResponse, HttpStatus.OK);
        }else{
            response = new ResponseEntity<GetCharacterResponse>(new GetCharacterResponse(), HttpStatus.BAD_REQUEST);
        }

        return response;
    }

    public ResponseEntity<GetInventoryResponse> getInventory(String characterId){
        return null;
    }

    public ResponseEntity<UpdateInventoryResponse> updateInventory(UpdateInventoryRequest request){
        return null;
    }

    private CharacterHistoryDto retrieveCharacterHistory(Integer historyId){
        CharacterHistoryDto historyDto = null;
        if(historyId != null){
            Optional<CharacterHistoryDto> dto = characterHistoryRepository.findById(historyId);
            historyDto = dto != null && dto.get() != null ? dto.get() : null;
        }
        return historyDto;
    }

    private CharacterDescriptionDto retrieveCharacterDescription(Integer descriptionId){
        CharacterDescriptionDto descriptionDto = null;
        if(descriptionId != null){
            Optional<CharacterDescriptionDto> dto = characterDescriptionRepository.findById(descriptionId);
            descriptionDto = dto != null && dto.get() != null ? dto.get() : null;
        }

        return descriptionDto;
    }

    private CharacterStatisticsDto retrieveCharacterStatistics(Integer characterId){
        CharacterStatisticsDto characterStatisticsDto = null;
        if(characterId != null){
            Optional<CharacterStatisticsDto> dto = characterStatisticRepository.findById(characterId);
            characterStatisticsDto = dto != null && dto.get() != null ? dto.get() : null;
        }

        return characterStatisticsDto;
    }
}

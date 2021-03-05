package com.novavrbe.vrbe.business;

import com.novavrbe.vrbe.dto.CharacterDto;
import com.novavrbe.vrbe.models.charactercontroller.*;
import com.novavrbe.vrbe.models.charactermodels.Character;
import com.novavrbe.vrbe.repositories.impl.CharacterRepositoryService;
import com.novavrbe.vrbe.utils.CharacterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class CharacterBusiness {

    @Autowired
    private CharacterRepositoryService characterRepositoryService;

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

        CharacterDto characterDto = characterRepositoryService.retrieveCharacterFromId(cID);
        if(characterDto != null){
            GetCharacterResponse getCharacterResponse = new GetCharacterResponse();

            Character character = new Character();
            CharacterUtils.fillCharacterFieldsFromDto(character, characterDto);
            CharacterUtils.fillCharacterHistoryFromDto(character, characterRepositoryService.retrieveCharacterHistory(cID));
            CharacterUtils.fillCharacterDescriptionFromDto(character, characterRepositoryService.retrieveCharacterDescription(cID));
            CharacterUtils.fillCharacterStatisticsFromDto(character, characterRepositoryService.retrieveCharacterStatistics(cID));
            CharacterUtils.fillCharacterTemporaryEffectsFromDto(character, characterRepositoryService.retrieveCharacterTemporaryEffects(cID));

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


}

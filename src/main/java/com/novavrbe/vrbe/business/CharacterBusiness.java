package com.novavrbe.vrbe.business;

import com.novavrbe.vrbe.dto.CharacterDto;
import com.novavrbe.vrbe.models.GetCharacterResponse;
import com.novavrbe.vrbe.models.GetInventoryResponse;
import com.novavrbe.vrbe.models.UpdateInventoryRequest;
import com.novavrbe.vrbe.models.UpdateInventoryResponse;
import com.novavrbe.vrbe.models.charactermodels.Character;
import com.novavrbe.vrbe.repositories.CharacterRepository;
import com.novavrbe.vrbe.utils.CharacterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CharacterBusiness {

    @Autowired
    private CharacterRepository characterRepository;

    public ResponseEntity<GetCharacterResponse> getCharacter(String characterId){
        ResponseEntity<GetCharacterResponse> response = null;

        if(!StringUtils.hasText(characterId)){
            response = new ResponseEntity<GetCharacterResponse>(new GetCharacterResponse(), HttpStatus.BAD_REQUEST);
            return response;
        }

        Integer cID = Integer.parseInt(characterId);

        Optional<CharacterDto> characterDto = characterRepository.findById(cID);
        if(characterDto != null){
            GetCharacterResponse getCharacterResponse = new GetCharacterResponse();

            Character character = new Character();
            CharacterUtils.fillCharacterFieldsFromDto(character, characterDto.get());

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

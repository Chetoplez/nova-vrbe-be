package com.novavrbe.vrbe.business;

import com.novavrbe.vrbe.dto.CharacterDto;
import com.novavrbe.vrbe.dto.InventoryDto;
import com.novavrbe.vrbe.models.charactercontroller.*;
import com.novavrbe.vrbe.models.charactermodels.Character;
import com.novavrbe.vrbe.models.charactermodels.Inventory;
import com.novavrbe.vrbe.repositories.impl.CharacterRepositoryService;
import com.novavrbe.vrbe.utils.CharacterUtils;
import com.novavrbe.vrbe.utils.ValidateUtils;
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
        ResponseEntity<AddCharacterResponse> response = null;

        if(addCharacterRequest.getCharacter() == null || !ValidateUtils.validateCharacter(addCharacterRequest.getCharacter())){
            response = new ResponseEntity<>(new AddCharacterResponse(), HttpStatus.BAD_REQUEST);
            return response;
        }

        AddCharacterResponse addCharacterResponse = new AddCharacterResponse();
        addCharacterResponse.setSuccess(characterRepositoryService.saveNewCharacter(addCharacterRequest.getUserId(), addCharacterRequest.getCharacter()));

        response = new ResponseEntity<>(addCharacterResponse, addCharacterResponse.isSuccess() ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);

        return response;
    }

    public ResponseEntity<GetCharacterResponse> getCharacter(String characterId){
        ResponseEntity<GetCharacterResponse> response = null;

        if(!StringUtils.hasText(characterId)){
            response = new ResponseEntity<>(new GetCharacterResponse(), HttpStatus.BAD_REQUEST);
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
        ResponseEntity<GetInventoryResponse> response = null;

        if(!StringUtils.hasText(characterId)){
            response = new ResponseEntity<>(new GetInventoryResponse(), HttpStatus.BAD_REQUEST);
            return response;
        }

        Integer charactedId = Integer.parseInt(characterId);

        InventoryDto inventoryDto = characterRepositoryService.retrieveCharacterInventory(charactedId);
        if(inventoryDto == null){
            response = new ResponseEntity<>(new GetInventoryResponse(), HttpStatus.BAD_REQUEST);
            return response;
        }

        Inventory inventory = new Inventory();
        CharacterUtils.mapInventoryDtoToInventory(inventory, inventoryDto);
        CharacterUtils.fillInventoryWithObjects(inventory, characterRepositoryService.retrieveCharacterObjects(charactedId));
        CharacterUtils.fillInventoryObjectsWithEffects(inventory, characterRepositoryService.retrieveInventoryObjectEffects(inventory));

        GetInventoryResponse inventoryResponse = new GetInventoryResponse();
        inventoryResponse.setInventory(inventory);
        response = new ResponseEntity<>(inventoryResponse, HttpStatus.OK);

        return response;
    }

    public ResponseEntity<UpdateInventoryResponse> updateInventory(UpdateInventoryRequest request){
        ResponseEntity<UpdateInventoryResponse> response = null;

        if(request == null || request.getInventory() == null || (request.getInventory() != null &&request.getInventory().getInventoryId() == null)){
            response = new ResponseEntity<>(new UpdateInventoryResponse(), HttpStatus.BAD_REQUEST);
            return response;
        }

        UpdateInventoryResponse inventoryResponse = new UpdateInventoryResponse();
        inventoryResponse.setInventory(request.getInventory());

        response = new ResponseEntity<>(inventoryResponse, characterRepositoryService.updateInventory(request.getInventory()) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);

        return response;
    }


}

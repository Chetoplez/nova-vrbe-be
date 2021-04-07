package com.novavrbe.vrbe.business;

import com.novavrbe.vrbe.dto.CharacterDto;
import com.novavrbe.vrbe.dto.InventoryDto;
import com.novavrbe.vrbe.dto.InventoryObjectDto;
import com.novavrbe.vrbe.dto.InventoryObjectEffectDto;
import com.novavrbe.vrbe.models.charactercontroller.*;
import com.novavrbe.vrbe.models.charactermodels.Character;
import com.novavrbe.vrbe.models.charactermodels.Inventory;
import com.novavrbe.vrbe.models.charactermodels.InventoryObjectAssociation;
import com.novavrbe.vrbe.repositories.impl.CharacterRepositoryService;
import com.novavrbe.vrbe.utils.CharacterUtils;
import com.novavrbe.vrbe.utils.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CharacterBusiness {

    @Autowired
    private CharacterRepositoryService characterRepositoryService;

    public ResponseEntity<AddCharacterResponse> addCharacter(AddCharacterRequest addCharacterRequest){
        ResponseEntity<AddCharacterResponse> response;

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

        CharacterDto characterDto = retrieveCharacterFromId(cID);
        if(characterDto != null){
            GetCharacterResponse getCharacterResponse = new GetCharacterResponse();

            Character character = new Character();
            CharacterUtils.fillCharacterFieldsFromDto(character, characterDto);
            CharacterUtils.fillCharacterHistoryFromDto(character, characterRepositoryService.retrieveCharacterHistory(cID));
            CharacterUtils.fillCharacterDescriptionFromDto(character, characterRepositoryService.retrieveCharacterDescription(cID));
            CharacterUtils.fillCharacterTemporaryEffectsFromDto(character, characterRepositoryService.retrieveCharacterTemporaryEffects(cID));
            CharacterUtils.fillCharacterJobFromDto(character, characterRepositoryService.retriveCharacterJob(cID));

            //TODO recuperare statistiche derivanti da equipaggiamento e Applicare a statistiche date da valori temporanee e armatura
            CharacterUtils.fillCharacterStatisticsFromDto(character, characterRepositoryService.retrieveCharacterStatistics(cID));

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


    public ResponseEntity<EquipItemResponse> equipItem(EquipItemRequest request) {
        ResponseEntity<EquipItemResponse> response = null;

        if(request == null || request.getCharacterId() == null || request.getItemId() == null){
            response = new ResponseEntity<>(new EquipItemResponse(), HttpStatus.BAD_REQUEST);
            return response;
        }

        CharacterDto character = retrieveCharacterFromId(request.getCharacterId());
        if(character == null){
            response = new ResponseEntity<>(new EquipItemResponse(), HttpStatus.BAD_REQUEST);
            return response;
        }

        List<InventoryObjectAssociation> objects = characterRepositoryService.retrieveCharacterObjects(request.getCharacterId());
        EquipItemResponse equipItemResponse = new EquipItemResponse();

        if(!CollectionUtils.isEmpty(objects)){
            List<InventoryObjectAssociation> itemDto = objects.stream().filter(association -> {
                return association.getCharacterInventoryObjectDto().getIdInventoryObject() == request.getItemId();
            }).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(itemDto)){
                InventoryObjectAssociation item = itemDto.get(0);

                //Equipaggiamento
                if(item.getInventoryObjectDto().isEquipment()){
                    //TODO GESTIRE IL CASO DELLE DUE MANI
                    item.getCharacterInventoryObjectDto().setInUse(request.isRemove() ? false : true);
                    characterRepositoryService.equipItem(item.getCharacterInventoryObjectDto());
                }else{
                    //Oggetto one shot
                    List<InventoryObjectEffectDto> effectDtoList = characterRepositoryService.retrieveInventoryObjectEffectsDto(item.getInventoryObjectDto().getId());
                    if(!CollectionUtils.isEmpty(effectDtoList)){
                        effectDtoList.stream().forEach( effect -> {
                            characterRepositoryService.applyEffect(request.getCharacterId(), effect);
                        });
                    }

                    characterRepositoryService.decreaseQuantityOrRemoveObject(request.getCharacterId(), item);
                }
                equipItemResponse.setEquipped(true);
            }
        }

        response = new ResponseEntity<>(equipItemResponse, HttpStatus.OK);

        return response;
    }

    public ResponseEntity<LendItemResponse> lendItem(LendItemRequest request) {
        ResponseEntity<LendItemResponse> response = null;

        if(request == null || request.getFromCharacterId() == null || request.getFromObjectId() == null || request.getToCharacterId() == null){
            response = new ResponseEntity<>(new LendItemResponse(), HttpStatus.BAD_REQUEST);
            return response;
        }

        LendItemResponse lendItemResponse = new LendItemResponse();

        List<InventoryObjectAssociation> objects = characterRepositoryService.retrieveCharacterObjects(request.getFromCharacterId());
        if(!CollectionUtils.isEmpty(objects)){
            List<InventoryObjectAssociation> items = objects.stream().filter(association -> {
                return association.getCharacterInventoryObjectDto().getIdInventoryObject() == request.getFromObjectId();
            }).collect(Collectors.toList());

            if(!CollectionUtils.isEmpty(items)){
                InventoryObjectAssociation item = items.get(0);
                characterRepositoryService.lendItemToAnotherCharacter(request.getToCharacterId(), item);
                lendItemResponse.setLended(true);
            }
        }

        response = new ResponseEntity<>(lendItemResponse, HttpStatus.OK);

        return response;
    }

    public ResponseEntity<AddItemResponse> addItem(AddItemRequest request) {
        ResponseEntity<AddItemResponse> response = null;

        if(request == null || request.getCharacterId() == null || request.getItemId() == null || request.getQuantity() == null){
            response = new ResponseEntity<>(new AddItemResponse(), HttpStatus.BAD_REQUEST);
            return response;
        }

        CharacterDto character = retrieveCharacterFromId(request.getCharacterId());
        InventoryObjectDto inventoryObjectDto = characterRepositoryService.retrieveInventoryItem(request.getItemId());

        if(character == null || inventoryObjectDto == null){
            response = new ResponseEntity<>(new AddItemResponse(), HttpStatus.BAD_REQUEST);
            return response;
        }

        AddItemResponse addItemResponse = new AddItemResponse();

        addItemResponse.setSuccess(characterRepositoryService.addItemToPlayerInventory(character.getCharacterId(), inventoryObjectDto, request.getQuantity()));
        response = new ResponseEntity<>(addItemResponse, HttpStatus.OK);

        return response;
    }

    private CharacterDto retrieveCharacterFromId(Integer id){
        CharacterDto character = characterRepositoryService.retrieveCharacterFromId(id);
        return  character;
    }
}

package com.novavrbe.vrbe.controllers;

import com.novavrbe.vrbe.business.CharacterBusiness;
import com.novavrbe.vrbe.models.charactercontroller.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/character")
public class CharacterController {

    @Autowired
    private CharacterBusiness characterBusiness;

    @PutMapping("/")
    public ResponseEntity<AddCharacterResponse> addCharacter(@RequestBody AddCharacterRequest addCharacterRequest){
        return characterBusiness.addCharacter(addCharacterRequest);
    }

    @GetMapping("/getcharacter/{characterId}")
    public ResponseEntity<GetCharacterResponse> getCharacter(@PathVariable String characterId){
        return characterBusiness.getCharacter(characterId);
    }

    @GetMapping("/getcharacter/unemployed")
    public ResponseEntity<GetListUnemployedResponse> getUnemployed(){
        return  characterBusiness.getUnEmployedCharacters();
    }

    @GetMapping("/getinventory/{characterId}")
    public ResponseEntity<GetInventoryResponse> getInventory(@PathVariable String characterId){
        return characterBusiness.getInventory(characterId);
    }

    @PatchMapping("/updateinventory")
    public ResponseEntity<UpdateInventoryResponse> updateInventory(@RequestBody UpdateInventoryRequest updateInventoryRequest){
        return characterBusiness.updateInventory(updateInventoryRequest);
    }

    @PatchMapping("/updatedescription")
    public ResponseEntity<UpdateDescriptionResponse> updateDescription(@RequestBody UpdateDescriptionRequest incomingRequest){
        return characterBusiness.updateDescription(incomingRequest);
    }

    @PatchMapping("/updatedehistory")
    public ResponseEntity<UpdateDescriptionResponse> updateHistory(@RequestBody UpdateDescriptionRequest incomingRequest){
        return characterBusiness.updateHistory(incomingRequest);
    }

    @PostMapping("/additem")
    public ResponseEntity<AddItemResponse> addItem(@RequestBody AddItemRequest addItemRequest){
        return characterBusiness.addItem(addItemRequest);
    }

    @PostMapping("/equip")
    public ResponseEntity<EquipItemResponse> equipItem(@RequestBody EquipItemRequest equipItemRequest){
        return characterBusiness.equipItem(equipItemRequest);
    }

    @PostMapping("/lenditem")
    public ResponseEntity<LendItemResponse> lendItem(@RequestBody LendItemRequest lendItemRequest){
        return characterBusiness.lendItem(lendItemRequest);
    }

}

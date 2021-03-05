package com.novavrbe.vrbe.controllers;

import com.novavrbe.vrbe.business.CharacterBusiness;
import com.novavrbe.vrbe.models.GetCharacterResponse;
import com.novavrbe.vrbe.models.GetInventoryResponse;
import com.novavrbe.vrbe.models.UpdateInventoryRequest;
import com.novavrbe.vrbe.models.UpdateInventoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/character")
public class CharacterController {

    @Autowired
    private CharacterBusiness characterBusiness;

    @GetMapping("getcharacter/{characterId}")
    public ResponseEntity<GetCharacterResponse> getCharacter(@PathVariable String characterId){
        return characterBusiness.getCharacter(characterId);
    }

    @GetMapping("getinventory/{characterId}")
    public ResponseEntity<GetInventoryResponse> getInventory(@PathVariable String characterId){
        return characterBusiness.getInventory(characterId);
    }

    @PostMapping("updateinventory")
    public ResponseEntity<UpdateInventoryResponse> updateInventory(@RequestBody UpdateInventoryRequest updateInventoryRequest){
        return characterBusiness.updateInventory(updateInventoryRequest);
    }
}

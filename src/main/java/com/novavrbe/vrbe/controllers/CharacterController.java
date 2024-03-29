package com.novavrbe.vrbe.controllers;

import com.novavrbe.vrbe.business.CharacterBusiness;
import com.novavrbe.vrbe.models.charactercontroller.CheckCharacterNomeResponse;
import com.novavrbe.vrbe.models.charactercontroller.*;
import com.novavrbe.vrbe.models.charactermodels.CheckCharacterNomeRequest;
import com.novavrbe.vrbe.models.charactermodels.GetAllCharacterResponse;
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

    @GetMapping("/getcharacterlist")
    public ResponseEntity<GetAllCharacterResponse> getAllCharacters(){
        return characterBusiness.getAllCharacters();
    }

    //@PostMapping("/checknome")
    //public ResponseEntity<CheckNomeResponse> checkNomeAvailability(@RequestBody CheckNomeRequest request){
    //    return characterBusiness.checkNomeAvailability(request);
    //}

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

    @PatchMapping("/updateimage")
    public ResponseEntity<UpdateDescriptionResponse> updateImage(@RequestBody UpdateImageRequest incomingRequest){
        return characterBusiness.updateImage(incomingRequest);
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

    @PostMapping ("/checknomevalidity")
    public ResponseEntity<CheckCharacterNomeResponse> checkNomePresence (@RequestBody CheckCharacterNomeRequest request){
        return characterBusiness.checkCharacterNomi(request);
    }

    @GetMapping("/prestavolto")
    public ResponseEntity<GetPrestavoltoResponse> getPrestavoltoList (){
        return characterBusiness.getPrestavoltoList();
    }

    @PatchMapping("/prestavolto/update")
    public  ResponseEntity<UpdatePrestavoltoResponse> updatePrestavolto(@RequestBody UpdatePrestavoltoRequest request){
        return characterBusiness.updatePrestavolto(request);
    }

}

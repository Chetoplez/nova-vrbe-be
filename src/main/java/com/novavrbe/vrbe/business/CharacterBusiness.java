package com.novavrbe.vrbe.business;

import com.novavrbe.vrbe.models.GetCharacterResponse;
import com.novavrbe.vrbe.models.GetInventoryResponse;
import com.novavrbe.vrbe.models.UpdateInventoryRequest;
import com.novavrbe.vrbe.models.UpdateInventoryResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CharacterBusiness {

    public ResponseEntity<GetCharacterResponse> getCharacter(String characterId){
        return null;
    }

    public ResponseEntity<GetInventoryResponse> getInventory(String characterId){
        return null;
    }

    public ResponseEntity<UpdateInventoryResponse> updateInventory(UpdateInventoryRequest request){
        return null;
    }
}

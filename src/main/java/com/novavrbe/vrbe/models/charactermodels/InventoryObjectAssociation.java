package com.novavrbe.vrbe.models.charactermodels;

import com.novavrbe.vrbe.dto.CharacterInventoryObjectDto;
import com.novavrbe.vrbe.dto.InventoryObjectDto;
import lombok.Data;

@Data
public class InventoryObjectAssociation {
    private InventoryObjectDto inventoryObjectDto;
    private CharacterInventoryObjectDto characterInventoryObjectDto;
}

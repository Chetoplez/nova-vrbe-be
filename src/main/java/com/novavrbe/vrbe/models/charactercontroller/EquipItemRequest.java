package com.novavrbe.vrbe.models.charactercontroller;

import lombok.Data;

@Data
public class EquipItemRequest {
    private Integer characterId;
    private Integer itemId;
    private boolean remove;
}

package com.novavrbe.vrbe.models.charactercontroller;

import lombok.Data;

@Data
public class AddItemRequest {
    private Integer characterId;
    private Integer itemId;
    private Integer quantity;
}

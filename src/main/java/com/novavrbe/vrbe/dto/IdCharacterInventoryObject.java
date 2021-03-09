package com.novavrbe.vrbe.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class IdCharacterInventoryObject implements Serializable {
    private Integer idInventoryObject;
    private Integer characterId;
}

package com.novavrbe.vrbe.models.charactercontroller;

import lombok.Data;

@Data
public class LendItemRequest {
    private Integer fromCharacterId;
    private Integer fromObjectId;
    private Integer toCharacterId;
}

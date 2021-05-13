package com.novavrbe.vrbe.models.charactercontroller;

import lombok.Data;

@Data
public class UpdateDescriptionRequest {
    private String characterId;
    private String newtext;
}

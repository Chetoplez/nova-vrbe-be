package com.novavrbe.vrbe.models.charactercontroller;

import com.novavrbe.vrbe.models.charactermodels.Character;
import lombok.Data;

@Data
public class AddCharacterRequest {
    private Integer userId;
    private Character character;
}

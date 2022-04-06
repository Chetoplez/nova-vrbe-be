package com.novavrbe.vrbe.models.charactercontroller;

import com.novavrbe.vrbe.models.charactermodels.Character;
import lombok.Data;

@Data
public class GetCharacterResponse {
    private boolean newpg;
    private Character character;
}

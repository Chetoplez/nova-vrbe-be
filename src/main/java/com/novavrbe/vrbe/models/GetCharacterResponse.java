package com.novavrbe.vrbe.models;

import com.novavrbe.vrbe.models.charactermodels.Character;
import lombok.Data;

@Data
public class GetCharacterResponse {
    private Character character;
}

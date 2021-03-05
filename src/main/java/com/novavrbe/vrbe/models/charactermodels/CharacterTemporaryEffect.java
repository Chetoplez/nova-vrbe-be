package com.novavrbe.vrbe.models.charactermodels;

import com.novavrbe.vrbe.models.enumerations.Stat;
import lombok.Data;

@Data
public class CharacterTemporaryEffect {
    private Integer id;
    private Integer modifier;
    private Stat stat;
}

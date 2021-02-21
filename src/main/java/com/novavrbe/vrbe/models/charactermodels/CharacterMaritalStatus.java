package com.novavrbe.vrbe.models.charactermodels;

import com.novavrbe.vrbe.models.enumerations.MaritalStatus;
import lombok.Data;

@Data
public class CharacterMaritalStatus {
    private MaritalStatus maritalStatus;
    private String characterId;
    private String characterName;
}

package com.novavrbe.vrbe.models.charactermodels;

import lombok.Data;

import java.util.ArrayList;

@Data
public class GetAllCharacterResponse {
    private ArrayList<SmallCharacter> characterList;
}

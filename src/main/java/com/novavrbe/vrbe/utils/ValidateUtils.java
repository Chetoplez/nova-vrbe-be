package com.novavrbe.vrbe.utils;

import com.novavrbe.vrbe.models.charactermodels.Character;
import org.springframework.util.StringUtils;

import java.util.HashMap;

public class ValidateUtils {

    private static final String CHARACTER_NAME = "characterName";
    private static final String CHARACTER_ICON = "characterIcon";
    private static final String GENDER = "gender";

    private static HashMap<String, Integer> maxFieldLenght = new HashMap<>();
    static {
        maxFieldLenght.put(CHARACTER_NAME, 50);
        maxFieldLenght.put(CHARACTER_ICON, 250);
        maxFieldLenght.put(GENDER, 2);
    }

    public static boolean validateCharacter(Character character){
        boolean valid = true;

        valid = validateString(CHARACTER_NAME, character.getCharacterName())
                && validateString(CHARACTER_ICON, character.getCharacterIcon() != null ? character.getCharacterIcon().toString() : "")
                && validateString(GENDER, character.getGender().toString());

        return valid;
    }

    private static boolean validateString(String field, String stringField){
        if(!StringUtils.hasText(stringField)){
            return false;
        }

        if(maxFieldLenght.get(field) != null){
            return stringField.length() <= maxFieldLenght.get(field);
        }

        return true;
    }
}

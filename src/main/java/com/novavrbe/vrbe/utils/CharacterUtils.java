package com.novavrbe.vrbe.utils;

import com.novavrbe.vrbe.dto.CharacterDescriptionDto;
import com.novavrbe.vrbe.dto.CharacterDto;
import com.novavrbe.vrbe.dto.CharacterHistoryDto;
import com.novavrbe.vrbe.models.charactermodels.Character;
import com.novavrbe.vrbe.models.charactermodels.CharacterDescription;
import com.novavrbe.vrbe.models.charactermodels.CharacterHistory;
import com.novavrbe.vrbe.models.charactermodels.CharacterLevel;
import com.novavrbe.vrbe.models.enumerations.Gender;
import com.novavrbe.vrbe.models.enumerations.HealthStatus;
import com.novavrbe.vrbe.models.enumerations.Roles;
import com.novavrbe.vrbe.models.enumerations.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class CharacterUtils {

    static Logger logger = LoggerFactory.getLogger(CharacterUtils.class);

    public static void fillCharacterFieldsFromDto(Character character, CharacterDto dto){
        if(character != null && dto != null){
            character.setCharacterId(dto.toString());
            character.setCharacterName(dto.getCharacterName());
            try {
                URL characterIcon = new URL(dto.getCharacterIcon());
                character.setCharacterIcon(characterIcon);
            } catch (MalformedURLException e) {
                logger.error(e.toString());
            }
            character.setGender(Gender.valueOf(dto.getGender()));
            character.setStatus(Status.valueOf(dto.getStatus()));

            CharacterLevel characterLevel = new CharacterLevel();
            characterLevel.setLevel(dto.getLevel());
            characterLevel.setExperience(dto.getExperience());
            characterLevel.setTotalExperience(dto.getTotalExperience());
            character.setLevel(characterLevel);

            character.setHealth(dto.getHealth());
            character.setHealthStatus(HealthStatus.valueOf(dto.getHealthStatus()));

            ArrayList<Roles> roles = new ArrayList<>();
            Roles role = Roles.valueOf(dto.getRole());
            roles.add(role);
            character.setRoles(roles);
        }
    }

    public static void fillCharacterHistoryFromDto(Character character, CharacterHistoryDto historyDto){
        if(character != null && historyDto != null){
            CharacterHistory history = new CharacterHistory();
            history.setHistory(historyDto.getHistory());
            history.setHistoryId(historyDto.getHistoryId());

            character.setHistory(history);
        }
    }

    public static void fillCharacterDescriptionFromDto(Character character, CharacterDescriptionDto descriptionDto){
        if(character != null && descriptionDto != null){
            CharacterDescription description = new CharacterDescription();
            description.setDescriptionId(descriptionDto.getDescriptionId());
            description.setDescription(descriptionDto.getDescription());

            character.setDescription(description);
        }
    }

}

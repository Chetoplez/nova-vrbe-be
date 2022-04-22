package com.novavrbe.vrbe.models.charactermodels;

import com.novavrbe.vrbe.models.enumerations.Gender;
import com.novavrbe.vrbe.models.enumerations.HealthStatus;
import com.novavrbe.vrbe.models.enumerations.Roles;
import com.novavrbe.vrbe.models.enumerations.Status;
import lombok.Data;

import java.net.URL;
import java.util.ArrayList;

@Data
public class Character {
    private String characterId;
    private String characterName;
    private String characterSurname;
    private String characterGens;
    private URL characterIcon;
    private CharacterJob characterJob;
    private URL characterImg;
    private CharacterHistory history;
    private CharacterDescription description;
    private Gender gender;
    private Status status;
    private CharacterLevel level;
    private ArrayList<CharacterStatistic> stats;
    private Integer health;
    private HealthStatus healthStatus;
    private ArrayList<CharacterTemporaryEffect> effects;
    private CharacterMaritalStatus maritalStatus;
    private ArrayList<Roles> roles;
}

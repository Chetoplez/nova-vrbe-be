package com.novavrbe.vrbe.models.charactermodels;

import com.novavrbe.vrbe.models.enumerations.Stat;
import lombok.Data;

@Data
public class CharacterStatistic {
    private Stat statName;
    private Integer baseStat;
    private Integer  modified;
}

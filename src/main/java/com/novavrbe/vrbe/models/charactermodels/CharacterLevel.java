package com.novavrbe.vrbe.models.charactermodels;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CharacterLevel {
    private Integer level;
    private BigDecimal experience;
    private BigDecimal totalExperience;
}

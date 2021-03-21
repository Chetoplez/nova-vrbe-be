package com.novavrbe.vrbe.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Table(name = "CharactersStatistics")
@Entity
public class CharacterStatisticsDto {
    @Id
    @Column(name = "CHARACTER_ID")
    private Integer characterId;
    @Column
    private Integer forza;
    @Column
    private BigDecimal forzaModifier;
    @Column
    private Integer destrezza;
    @Column
    private BigDecimal destrezzaModifier;
    @Column
    private Integer costituzione;
    @Column
    private BigDecimal costituzioneModifier;
    @Column
    private Integer intelligenza;
    @Column
    private BigDecimal intelligenzaModifier;
    @Column
    private Integer saggezza;
    @Column
    private BigDecimal saggezzaModifier;
}

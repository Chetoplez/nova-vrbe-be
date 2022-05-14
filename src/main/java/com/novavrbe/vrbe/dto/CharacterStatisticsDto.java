package com.novavrbe.vrbe.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
    private Integer forzaModifier;
    @Column
    private Integer destrezza;
    @Column
    private Integer destrezzaModifier;
    @Column
    private Integer costituzione;
    @Column
    private Integer costituzioneModifier;
    @Column
    private Integer intelligenza;
    @Column
    private Integer intelligenzaModifier;
    @Column
    private Integer saggezza;
    @Column
    private Integer saggezzaModifier;
}

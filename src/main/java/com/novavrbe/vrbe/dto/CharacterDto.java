package com.novavrbe.vrbe.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Table(name = "Characters")
@Data
@Entity
public class CharacterDto {
    @Id
    @Column(name = "CHARACTER_ID")
    private Integer characterId;
    @Column(name = "CHARACTER_NAME")
    private String characterName;
    @Column(name = "CHARACTER_SURNAME")
    private String characterSurname;
    @Column(name = "CHARACTER_GENS")
    private String characterGens;
    @Column(name = "CHARACTER_ICON")
    private String characterIcon;
    @Column
    private String characterImg;
    @Column
    private String gender;
    @Column
    private String status;
    @Column(name = "clevel")
    private Integer level;
    @Column
    private BigDecimal experience;
    @Column(name = "TOTAL_EXPERIENCE")
    private BigDecimal totalExperience;
    @Column
    private Integer health;
    @Column(name = "HEALTH_STATUS")
    private String healthStatus;
    @Column
    private String role; //ruolo del pg dentro la land. Possibili stati: COMMON,

}

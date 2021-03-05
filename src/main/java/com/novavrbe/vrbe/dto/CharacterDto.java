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
    private BigDecimal characterId;
    @Column
    private String characterName;
    @Column
    private String characterIcon;
    @Column
    private String gender;
    @Column
    private String status;
    @Column(name = "clevel")
    private Integer level;
    @Column
    private BigDecimal experience;
    @Column
    private BigDecimal totalExperience;
    @Column
    private String healthStatus;
    @Column
    private String role;

}

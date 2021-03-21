package com.novavrbe.vrbe.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "CharactersDescription")
@Entity
public class CharacterDescriptionDto {
    @Id
    @Column(name = "DESCRIPTION_ID")
    private Integer descriptionId;
    @Column
    private String description;
}

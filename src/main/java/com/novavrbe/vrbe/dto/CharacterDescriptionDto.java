package com.novavrbe.vrbe.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;

@Data

public class CharacterDescriptionDto {
    @Id
    private Integer descriptionId;
    @Column
    private String description;
}

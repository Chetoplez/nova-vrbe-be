package com.novavrbe.vrbe.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table
@Entity
public class CharacterDescriptionDto {
    @Id
    private Integer descriptionId;
    @Column
    private String description;
}

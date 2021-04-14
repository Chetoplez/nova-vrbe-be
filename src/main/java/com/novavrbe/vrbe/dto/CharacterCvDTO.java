package com.novavrbe.vrbe.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity

@Table (name = "Character_CV")
public class CharacterCvDTO {
    @Id
    @Column(name = "CHARACTER_ID")
    private Integer characterId;

    @Column(name = "role_id")
    private Integer roleId;

    @Column(name = "ENROLLMENT_DATE")
    private java.sql.Date enrollmentDate;
}

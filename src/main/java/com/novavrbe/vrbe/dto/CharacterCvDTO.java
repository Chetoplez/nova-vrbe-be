package com.novavrbe.vrbe.dto;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@IdClass(IdCharacterCv.class)
@Table (name = "Character_CV")
public class CharacterCvDTO {
    @Id
    @Column(name = "CHARACTER_ID")
    private Integer characterId;

    @Column(name = "role_id")
    private Integer roleId;
    @Id
    @Column(name = "ENROLLMENT_DATE")
    private Date enrollmentDate;
}

package com.novavrbe.vrbe.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

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

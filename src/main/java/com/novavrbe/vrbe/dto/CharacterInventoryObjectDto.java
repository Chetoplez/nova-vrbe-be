package com.novavrbe.vrbe.dto;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Table(name="CharacterInventoryObject")
@Entity
@Data
@IdClass(IdCharacterInventoryObject.class)
public class CharacterInventoryObjectDto implements Serializable{
    @Id
    private Integer idInventoryObject;
    @Id
    private Integer characterId;
    @Column
    private Integer quantity;
    @Column
    private Boolean inUse;
    @Column
    private Long duration;
    @Column
    private Date acquiringDate;
    @Column
    private Integer acquiredBy;
}

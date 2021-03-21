package com.novavrbe.vrbe.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "InventoryObject")
@Entity
public class InventoryObjectDto {
    @Id
    private Integer id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private boolean isEquipment;
    @Column
    private boolean isRare;
    @Column
    private String category;
    @Column
    private String bodyPart;
    @Column
    private Long duration;
    @Column
    private String url;
}

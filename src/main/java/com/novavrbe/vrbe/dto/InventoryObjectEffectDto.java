package com.novavrbe.vrbe.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "InventoryObjectEffect")
@Entity
@Data
public class InventoryObjectEffectDto {
    @Id
    private Integer effect_id;
    @Column
    private Integer inventoryObjectId;
    @Column
    private String healthStatus;
    @Column
    private Integer healing;
    @Column
    private String stat;
    @Column
    private Integer modifier;
    @Column
    private Boolean isTemporary;
    @Column
    private Long duration;
    @Column
    private Boolean isOneShot;
}

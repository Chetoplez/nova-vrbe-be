package com.novavrbe.vrbe.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "Inventory")
@Data
public class InventoryDto {
    @Id
    @Column(name = "CHARACTER_ID")
    private Integer characterId;
    @Column
    private Integer gold;
}

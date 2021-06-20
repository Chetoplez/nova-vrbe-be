package com.novavrbe.vrbe.dto;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "MarketItem")
@Data
public class MarketItemDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer marketItemId;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private Integer inventoryObjectId;
    @Column
    private Integer price;
    @Column
    private Integer quantity;
    @Column
    private Long expiresOn;
    @Column
    private boolean illimited;
}

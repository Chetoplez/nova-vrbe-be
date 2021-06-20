package com.novavrbe.vrbe.models.marketcontroller;

import com.novavrbe.vrbe.models.charactermodels.InventoryObject;
import lombok.Data;

@Data
public class MarketItem {

    private Integer itemId;
    private String name;
    private String description;
    private InventoryObject item;
    private Integer quantity;
    private boolean illimited;
    private Integer price;
    private Long expiresOn;
}

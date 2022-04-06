package com.novavrbe.vrbe.models.charactermodels;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Inventory {
    private Integer inventoryId;
    private Integer gold;
    private ArrayList<InventoryObject> items;

    public Inventory() {
        this.items = new ArrayList<InventoryObject>();
    }
}

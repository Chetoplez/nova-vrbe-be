package com.novavrbe.vrbe.models.charactermodels;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Inventory {
    private String inventoryId;
    private Integer gold;
    private ArrayList<InventoryObject> items;
}

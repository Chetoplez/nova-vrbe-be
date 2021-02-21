package com.novavrbe.vrbe.models.charactermodels;

import lombok.Data;

import java.util.ArrayList;

@Data
public class InventoryObject {

    private String id;
    private String name;
    private String description;
    private boolean inUse;
    private ArrayList<InventoryObjectEffect> modifiers;
}

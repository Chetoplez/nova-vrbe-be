package com.novavrbe.vrbe.models.charactermodels;

import com.novavrbe.vrbe.models.enumerations.BodyPart;
import lombok.Data;

import java.util.ArrayList;

@Data
public class InventoryObject {
    private String id;
    private String name;
    private String description;
    private Integer quantity;
    private boolean isEquipment;
    private boolean inUse;
    private boolean isRare;
    private BodyPart bodyPart;
    private ArrayList<InventoryObjectEffect> modifiers;
}

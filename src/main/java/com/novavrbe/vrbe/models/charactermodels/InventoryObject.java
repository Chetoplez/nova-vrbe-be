package com.novavrbe.vrbe.models.charactermodels;

import com.novavrbe.vrbe.models.enumerations.BodyPart;
import com.novavrbe.vrbe.models.enumerations.ObjectCategory;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;

@Data
public class InventoryObject {
    private Integer id;
    private String name;
    private String description;
    private Integer quantity;
    private boolean isEquipment;
    private boolean inUse;
    private boolean isRare;
    private ObjectCategory category;
    private BodyPart bodyPart;
    private Date acquiringDate;
    private Long duration;
    private ArrayList<InventoryObjectEffect> modifiers;
}

package com.novavrbe.vrbe.models.charactermodels;

import com.novavrbe.vrbe.dto.InventoryObjectEffectDto;
import lombok.Data;

import java.util.List;

@Data
public class InventoryObjectEffectAssociation {
    private Integer objectId;
    private List<InventoryObjectEffectDto> effects;
}

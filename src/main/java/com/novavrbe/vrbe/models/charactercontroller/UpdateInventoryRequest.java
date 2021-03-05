package com.novavrbe.vrbe.models.charactercontroller;

import com.novavrbe.vrbe.models.charactermodels.Inventory;
import lombok.Data;

@Data
public class UpdateInventoryRequest {
    private Inventory inventory;
}

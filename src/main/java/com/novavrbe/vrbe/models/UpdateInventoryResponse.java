package com.novavrbe.vrbe.models;

import com.novavrbe.vrbe.models.charactermodels.Inventory;
import lombok.Data;

@Data
public class UpdateInventoryResponse {
    private Inventory inventory;
}

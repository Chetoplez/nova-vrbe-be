package com.novavrbe.vrbe.models;

import com.novavrbe.vrbe.models.charactermodels.Inventory;
import lombok.Data;

@Data
public class GetInventoryResponse {
    private Inventory inventory;
}

package com.novavrbe.vrbe.models.charactermodels;

import com.novavrbe.vrbe.models.enumerations.HealthStatus;
import com.novavrbe.vrbe.models.enumerations.Stat;
import lombok.Data;

@Data
public class InventoryObjectEffect {
    private HealthStatus healthStatus;
    private Stat stat;
    private boolean isTemporary;
    private boolean isOneShot;
}

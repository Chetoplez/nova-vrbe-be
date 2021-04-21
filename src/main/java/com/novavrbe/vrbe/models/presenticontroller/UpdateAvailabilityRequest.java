package com.novavrbe.vrbe.models.presenticontroller;

import lombok.Data;

@Data
public class UpdateAvailabilityRequest {
    private String characterId;
    private String available;
}

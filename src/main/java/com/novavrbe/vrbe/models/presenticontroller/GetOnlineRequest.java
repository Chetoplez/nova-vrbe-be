package com.novavrbe.vrbe.models.presenticontroller;

import lombok.Data;

@Data
public class GetOnlineRequest {
    private String characterId;
    private boolean online;
}

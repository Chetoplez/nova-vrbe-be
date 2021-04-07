package com.novavrbe.vrbe.models.guildcontroller;

import lombok.Data;

@Data
public class CheckGuildPermissionResponse {
    private boolean present;
    private boolean manager;
}

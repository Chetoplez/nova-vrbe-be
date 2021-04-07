package com.novavrbe.vrbe.models.guildcontroller;

import lombok.Data;

@Data
public class CheckGuildPermissionRequest {
    private String characterId;
    private String guildId;
}

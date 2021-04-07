package com.novavrbe.vrbe.models.guildcontroller;

import lombok.Data;

@Data
public class PromoteMemberRequest {
    private String character_id;
    private String guild_id;
}

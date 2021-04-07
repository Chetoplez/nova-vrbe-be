package com.novavrbe.vrbe.models.guildcontroller;

import lombok.Data;

@Data
public class DeleteMemberRequest {
    private String character_id;
    private String role_id;
}

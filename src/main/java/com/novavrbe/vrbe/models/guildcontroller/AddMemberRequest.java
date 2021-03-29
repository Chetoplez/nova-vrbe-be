package com.novavrbe.vrbe.models.guildcontroller;

import lombok.Data;

@Data
public class AddMemberRequest {

    private String character_id;
    private String role_id;

}

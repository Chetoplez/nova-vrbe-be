package com.novavrbe.vrbe.models.charactermodels;

import lombok.Data;

@Data
public class CharacterJob {
    private Integer guildId;
    private Integer roleId;
    private String roleName;
    private String role_img;
    private String specification;
}

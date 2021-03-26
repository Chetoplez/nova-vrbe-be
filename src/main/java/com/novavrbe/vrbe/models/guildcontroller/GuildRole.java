package com.novavrbe.vrbe.models.guildcontroller;

import lombok.Data;

@Data
public class GuildRole {
    private Integer role_id;
    private Integer guild_id;
    private String name;
    private Integer salary;
    private Boolean isManager;
    private String role_img;
    private String description;
    private Integer guild_level;
}

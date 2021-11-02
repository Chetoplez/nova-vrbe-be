package com.novavrbe.vrbe.models.guildcontroller;

import lombok.Data;

@Data
public class GuildMember {


    private Integer GUILD_ID;

    private Integer ROLE_ID;

    private Integer CHARACTER_ID;

    private String CHARACTER_NAME;

    private Integer GUILD_LEVEL;

    private String ROLE_NAME;

    private String ROLE_IMG;

    private Integer ROLESALARY;
}

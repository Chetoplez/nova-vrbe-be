package com.novavrbe.vrbe.models.guildcontroller;

import lombok.Data;

import java.util.List;

@Data
public class GetGuildRoleReponse {
    private List<GuildRole> guildRoleList;
}

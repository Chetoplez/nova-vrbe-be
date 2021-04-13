package com.novavrbe.vrbe.models.guildcontroller;

import lombok.Data;

import java.util.List;

@Data
public class GetGuildMemberResponse {
    private List<GuildMember> members;
}

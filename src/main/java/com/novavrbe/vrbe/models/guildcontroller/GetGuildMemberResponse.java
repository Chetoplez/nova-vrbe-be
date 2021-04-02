package com.novavrbe.vrbe.models.guildcontroller;

import com.novavrbe.vrbe.dto.GuildMemberListDTO;
import lombok.Data;

import java.util.List;

@Data
public class GetGuildMemberResponse {
    private List<GuildMember> members;
}

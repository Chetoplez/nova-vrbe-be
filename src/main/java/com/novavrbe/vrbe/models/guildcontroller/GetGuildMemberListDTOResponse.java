package com.novavrbe.vrbe.models.guildcontroller;

import com.novavrbe.vrbe.dto.GuildMemberListDTO;
import lombok.Data;

import java.util.List;

@Data
public class GetGuildMemberListDTOResponse {
    private List<GuildMemberListDTO> members;
}

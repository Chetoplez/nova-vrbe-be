package com.novavrbe.vrbe.repositories.impl;

import com.novavrbe.vrbe.dto.GuildDTO;
import com.novavrbe.vrbe.repositories.GuildRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GuildRepositoryService {

    @Autowired
    private GuildRepository guildRepository;

    public GuildDTO getGuildById(Integer guildId) {
        GuildDTO guildDTO = null;
        if (guildId != null) {
            Optional<GuildDTO> dto = guildRepository.findById(guildId);
            guildDTO = dto.isPresent() ? dto.get() : null;
        }

        return guildDTO;
    }
}

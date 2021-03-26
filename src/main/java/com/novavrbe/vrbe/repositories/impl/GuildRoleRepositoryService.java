package com.novavrbe.vrbe.repositories.impl;

import com.novavrbe.vrbe.dto.GuildRoleDTO;
import com.novavrbe.vrbe.repositories.GuildRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GuildRoleRepositoryService {

    @Autowired
    private GuildRoleRepository guildRoleRepository;

    public List<GuildRoleDTO> getRoleByGuildId(Integer guildId) {
        List<GuildRoleDTO> tmp = new ArrayList<>();
        if(guildId != null){
             tmp = guildRoleRepository.findAllGuildRoleById(guildId);
        }
    return tmp  ;
    }
}

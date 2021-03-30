package com.novavrbe.vrbe.repositories;

import com.novavrbe.vrbe.dto.GuildRoleDTO;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GuildRoleRepository extends CrudRepository<GuildRoleDTO,Integer>, JpaSpecificationExecutor<GuildRoleDTO> {

    default  List<GuildRoleDTO> findAllGuildRoleById(Integer guildId){
        return findAll(
                (root, query, criteriaBuilder)-> criteriaBuilder.equal(root.get("guild_id"), guildId)
        );

    }

    //TODO da implementare una query: SELECT TOP 1 ROLE_ID FROM GUILDROLES WHERE GUILD_ID = guild_id and GUILD_LEVEL > guild_level order by GUILD_LEVEL ASC
    default Integer getNextRoleId(Integer guild_id, Integer guild_level){

        return null;
    }
}

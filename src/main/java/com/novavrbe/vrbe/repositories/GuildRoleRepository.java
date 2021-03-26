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
}

package com.novavrbe.vrbe.repositories;

import com.novavrbe.vrbe.dto.GuildRoleDTO;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GuildRoleRepository extends CrudRepository<GuildRoleDTO,Integer> {

    List<GuildRoleDTO> findByGuildId(Integer guildId);

    GuildRoleDTO findByRoleId(Integer roleId);

    List<GuildRoleDTO> findByGuildIdAndGuildLevelGreaterThan(Integer guild_id, Integer guild_level);

    List<GuildRoleDTO> findByGuildIdAndGuildLevelLessThan(Integer guild_id, Integer guild_level);
}

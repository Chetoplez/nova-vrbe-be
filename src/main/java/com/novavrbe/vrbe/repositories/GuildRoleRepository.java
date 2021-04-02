package com.novavrbe.vrbe.repositories;

import com.novavrbe.vrbe.dto.GuildRoleDTO;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GuildRoleRepository extends CrudRepository<GuildRoleDTO,Integer>, JpaSpecificationExecutor<GuildRoleDTO> {

    default  List<GuildRoleDTO> findAllGuildRoleById(Integer guildId){
        return findAll(
                (root, query, criteriaBuilder)-> criteriaBuilder.equal(root.get("guild_id"), guildId)
        );

    }


    default List<GuildRoleDTO> getPossibleNextRoles(Integer guild_id, Integer guild_level){
        Specification<GuildRoleDTO> levelSpec = (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("guild_level"), guild_level);
        Specification<GuildRoleDTO> guildId = (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("guild_id"),guild_id);

        return findAll(Specification.where(levelSpec).and(guildId));
    }

    default List<GuildRoleDTO> getPossiblePrevtRoles(Integer guild_id, Integer guild_level){
        Specification<GuildRoleDTO> levelSpec = (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThan(root.get("guild_level"), guild_level);
        Specification<GuildRoleDTO> guildId = (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("guild_id"),guild_id);

        return findAll(Specification.where(levelSpec).and(guildId));
    }
}

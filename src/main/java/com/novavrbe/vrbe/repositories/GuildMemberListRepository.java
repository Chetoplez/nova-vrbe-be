package com.novavrbe.vrbe.repositories;


import com.novavrbe.vrbe.dto.GuildMemberListDTO;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface GuildMemberListRepository extends CrudRepository<GuildMemberListDTO, Integer>, JpaSpecificationExecutor<GuildMemberListDTO> {

    default List<GuildMemberListDTO> findMembers(Integer guildId) {
        return findAll(
                (root, query, criteriaBuilder)-> criteriaBuilder.equal(root.get("GUILD_ID"), guildId)
        );
    }

    default GuildMemberListDTO getGuildMemberbyID(Integer characterid){
        Optional<GuildMemberListDTO> dto = findById(characterid);
        GuildMemberListDTO member = dto.isPresent() ? dto.get() : null;
        return member;

    }
}

package com.novavrbe.vrbe.repositories;


import com.novavrbe.vrbe.dto.GuildMemberListDTO;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface GuildMemberListRepository extends CrudRepository<GuildMemberListDTO, Integer>, JpaSpecificationExecutor<GuildMemberListDTO> {
    default List<GuildMemberListDTO> findMembers(Integer guildId) {
        return findAll(
                (root, query, criteriaBuilder)-> criteriaBuilder.equal(root.get("GUILD_ID"), guildId)
        );
    }
}

package com.novavrbe.vrbe.repositories;


import com.novavrbe.vrbe.dto.V_GuildMembers;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface GuildMemberListRepository extends CrudRepository<V_GuildMembers, Integer> , JpaSpecificationExecutor<V_GuildMembers>{
    Optional<V_GuildMembers> findByCharacterId(int id);
    List<V_GuildMembers> findAllByGuildId(int id);

}

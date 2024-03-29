package com.novavrbe.vrbe.repositories;

import com.novavrbe.vrbe.dto.GuildDTO;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface GuildRepository extends CrudRepository<GuildDTO, Integer>, JpaSpecificationExecutor<GuildDTO> {
}

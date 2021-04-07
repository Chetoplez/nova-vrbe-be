package com.novavrbe.vrbe.repositories;

import com.novavrbe.vrbe.dto.GuildBankDTO;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface GuildBankRepository extends CrudRepository<GuildBankDTO,Integer>, JpaSpecificationExecutor<GuildBankDTO> {
}

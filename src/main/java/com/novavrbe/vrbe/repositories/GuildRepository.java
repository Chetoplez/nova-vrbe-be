package com.novavrbe.vrbe.repositories;

import com.novavrbe.vrbe.models.Guild;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;

public interface GuildRepository extends CrudRepository<Guild, BigDecimal> {
}

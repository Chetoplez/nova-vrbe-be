package com.novavrbe.vrbe.repositories;

import com.novavrbe.vrbe.dto.DailyExpDto;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.util.Optional;

public interface DailyExpRepository extends CrudRepository<DailyExpDto, Integer> {
    Optional<DailyExpDto> findByCharacterIdAndExpDateEquals(Integer characterId, Date date);
}

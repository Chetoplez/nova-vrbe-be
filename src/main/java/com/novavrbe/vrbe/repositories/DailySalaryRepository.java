package com.novavrbe.vrbe.repositories;


import com.novavrbe.vrbe.dto.DailySalaryDto;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DailySalaryRepository extends CrudRepository<DailySalaryDto, Integer> {
    Optional<DailySalaryDto> findByCharacterId(Integer characterId);
}

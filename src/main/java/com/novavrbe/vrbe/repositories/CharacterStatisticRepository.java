package com.novavrbe.vrbe.repositories;

import com.novavrbe.vrbe.dto.CharacterStatisticsDto;
import org.springframework.data.repository.CrudRepository;

public interface CharacterStatisticRepository extends CrudRepository<CharacterStatisticsDto, Integer> {
}

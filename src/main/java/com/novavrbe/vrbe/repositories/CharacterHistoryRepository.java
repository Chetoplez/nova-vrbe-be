package com.novavrbe.vrbe.repositories;

import com.novavrbe.vrbe.dto.CharacterHistoryDto;
import org.springframework.data.repository.CrudRepository;

public interface CharacterHistoryRepository extends CrudRepository<CharacterHistoryDto, Integer> {
}

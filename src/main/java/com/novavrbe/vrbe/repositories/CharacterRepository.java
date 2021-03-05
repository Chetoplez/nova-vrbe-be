package com.novavrbe.vrbe.repositories;

import com.novavrbe.vrbe.dto.CharacterDto;
import org.springframework.data.repository.CrudRepository;

public interface CharacterRepository extends CrudRepository<CharacterDto, Integer> {
}

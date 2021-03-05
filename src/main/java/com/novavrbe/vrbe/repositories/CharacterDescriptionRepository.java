package com.novavrbe.vrbe.repositories;

import com.novavrbe.vrbe.dto.CharacterDescriptionDto;
import org.springframework.data.repository.CrudRepository;

public interface CharacterDescriptionRepository extends CrudRepository<CharacterDescriptionDto, Integer> {
}

package com.novavrbe.vrbe.repositories;

import com.novavrbe.vrbe.dto.CharacterCvDTO;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CharacterCvRepository extends CrudRepository<CharacterCvDTO, Integer> {
    List<CharacterCvDTO> findAllByCharacterIdOrderByEnrollmentDateDesc(Integer cId);
}

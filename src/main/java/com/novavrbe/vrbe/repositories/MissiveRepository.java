package com.novavrbe.vrbe.repositories;

import com.novavrbe.vrbe.dto.MissivaDto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MissiveRepository extends CrudRepository<MissivaDto, Integer> {
    List<MissivaDto> findByChToAndDeletedFalse(String parseInt);
}

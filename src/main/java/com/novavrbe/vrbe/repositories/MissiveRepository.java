package com.novavrbe.vrbe.repositories;

import com.novavrbe.vrbe.dto.MissivaDto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public interface MissiveRepository extends CrudRepository<MissivaDto, Integer> {
    List<MissivaDto> findByChToAndDeletedToFalse(String parseInt);

    ArrayList<MissivaDto> findByChToAndDeletedToFalseAndIsReadFalse(String chId);

    List<MissivaDto> findByChFromAndDeletedFromFalse(Integer from);

}

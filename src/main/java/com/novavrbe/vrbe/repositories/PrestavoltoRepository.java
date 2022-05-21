package com.novavrbe.vrbe.repositories;

import com.novavrbe.vrbe.dto.PrestavoltoDto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public interface PrestavoltoRepository extends CrudRepository<PrestavoltoDto, Integer> {
}

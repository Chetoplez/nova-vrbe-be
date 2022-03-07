package com.novavrbe.vrbe.repositories;

import com.novavrbe.vrbe.dto.ForumDTO;
import org.springframework.data.repository.CrudRepository;

public interface ForumRepository extends CrudRepository<ForumDTO,Integer> {
    Iterable<ForumDTO> findByAdminOnlyTrue();

    Iterable<ForumDTO> findByAdminOnlyFalse();
}

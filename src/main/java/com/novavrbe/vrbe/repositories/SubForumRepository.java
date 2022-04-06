package com.novavrbe.vrbe.repositories;

import com.novavrbe.vrbe.dto.SubForumDTO;
import org.springframework.data.repository.CrudRepository;

public interface SubForumRepository extends CrudRepository<SubForumDTO,Integer> {
    Iterable<SubForumDTO> findByForumId(Integer forumId);

    Iterable<SubForumDTO> findByForumIdAndAdminOnlyFalse(Integer forumId);
}

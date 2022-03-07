package com.novavrbe.vrbe.repositories;

import com.novavrbe.vrbe.dto.PostDTO;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<PostDTO,Integer> {
    Iterable<PostDTO> findBySubforumId(int subforumId);
}

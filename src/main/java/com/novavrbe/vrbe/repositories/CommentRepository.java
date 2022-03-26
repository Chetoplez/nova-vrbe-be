package com.novavrbe.vrbe.repositories;

import com.novavrbe.vrbe.dto.CommentDTO;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<CommentDTO, Integer> {
    Iterable<CommentDTO> findByPostId(int parseInt);

    Iterable<CommentDTO> findByRelatedComment(int parseInt);
}

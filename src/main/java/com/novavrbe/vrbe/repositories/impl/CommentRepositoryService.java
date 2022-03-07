package com.novavrbe.vrbe.repositories.impl;

import com.novavrbe.vrbe.dto.CommentDTO;
import com.novavrbe.vrbe.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentRepositoryService {

    @Autowired
    CommentRepository commentRepository;

    public Integer createComment(CommentDTO newDto) {
        CommentDTO dto = commentRepository.save(newDto);
        return dto.getCommentID();
    }

    public Iterable<CommentDTO> getPostComments(int parseInt) {
        Iterable<CommentDTO> dtos = commentRepository.findByPostId(parseInt);
        return dtos;
    }
}

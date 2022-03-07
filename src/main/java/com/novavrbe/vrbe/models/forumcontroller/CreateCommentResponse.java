package com.novavrbe.vrbe.models.forumcontroller;

import lombok.Data;

@Data
public class CreateCommentResponse {
    private Integer commentId;
    private String message;
}

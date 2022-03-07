package com.novavrbe.vrbe.models.forumcontroller;

import lombok.Data;

@Data
public class CreatePostResponse {
    private Integer postId;
    private String message;
}

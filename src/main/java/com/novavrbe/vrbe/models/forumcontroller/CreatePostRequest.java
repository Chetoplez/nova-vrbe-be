package com.novavrbe.vrbe.models.forumcontroller;

import lombok.Data;

@Data
public class CreatePostRequest {
    private Integer chId;
    private Post post;
}

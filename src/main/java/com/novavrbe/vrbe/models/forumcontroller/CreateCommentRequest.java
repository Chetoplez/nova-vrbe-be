package com.novavrbe.vrbe.models.forumcontroller;

import lombok.Data;

@Data
public class CreateCommentRequest {
    private Integer chId;
    private Integer postId;
    private Comment comment;

}

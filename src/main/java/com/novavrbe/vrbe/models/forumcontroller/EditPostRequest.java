package com.novavrbe.vrbe.models.forumcontroller;

import lombok.Data;

@Data
public class EditPostRequest {
    private Integer chId;
    private Integer postId;
    private Post editedPost;
}

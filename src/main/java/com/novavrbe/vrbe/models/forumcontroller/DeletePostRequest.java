package com.novavrbe.vrbe.models.forumcontroller;

import lombok.Data;

@Data
public class DeletePostRequest {
    private Integer chId;
    private Integer postId;
}

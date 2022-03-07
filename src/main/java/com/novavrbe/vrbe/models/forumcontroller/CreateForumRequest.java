package com.novavrbe.vrbe.models.forumcontroller;

import lombok.Data;

@Data
public class CreateForumRequest {
    private Integer chId;
    private Forum forum;
}

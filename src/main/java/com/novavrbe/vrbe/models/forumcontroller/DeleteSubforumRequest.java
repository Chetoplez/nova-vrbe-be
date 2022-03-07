package com.novavrbe.vrbe.models.forumcontroller;

import lombok.Data;

@Data
public class DeleteSubforumRequest {
    private Integer chId;
    private Integer subForumId;
}

package com.novavrbe.vrbe.models.forumcontroller;

import lombok.Data;

@Data
public class CreateSubForumRequest {
    private Integer chId;
    private SubForum subForum;

}

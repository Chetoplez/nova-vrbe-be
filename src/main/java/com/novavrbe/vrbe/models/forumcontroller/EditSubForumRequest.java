package com.novavrbe.vrbe.models.forumcontroller;

import lombok.Data;

@Data
public class EditSubForumRequest {
    private Integer chId;
    private Integer subforumId;
    private SubForum subForum;
}

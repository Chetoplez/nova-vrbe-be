package com.novavrbe.vrbe.models.forumcontroller;

import com.novavrbe.vrbe.dto.ForumDTO;
import lombok.Data;

@Data
public class UpdateForumRequest {
    private Integer chId;
    private ForumDTO newForum;
}

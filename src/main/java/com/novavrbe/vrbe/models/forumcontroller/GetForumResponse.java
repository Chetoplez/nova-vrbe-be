package com.novavrbe.vrbe.models.forumcontroller;

import com.novavrbe.vrbe.dto.ForumDTO;
import lombok.Data;

import java.util.ArrayList;

@Data
public class GetForumResponse {
    private ArrayList<ForumDTO> forumsList;
}

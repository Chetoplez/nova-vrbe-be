package com.novavrbe.vrbe.models.forumcontroller;

import com.novavrbe.vrbe.dto.PostDTO;
import lombok.Data;

import java.util.ArrayList;

@Data
public class GetPostListResponse {
    private ArrayList<PostDTO> postList;
}

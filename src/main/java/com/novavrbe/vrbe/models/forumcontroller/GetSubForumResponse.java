package com.novavrbe.vrbe.models.forumcontroller;

import com.novavrbe.vrbe.dto.SubForumDTO;
import lombok.Data;

import java.util.ArrayList;

@Data
public class GetSubForumResponse {
    private ArrayList<SubForumDTO> subForums;
}

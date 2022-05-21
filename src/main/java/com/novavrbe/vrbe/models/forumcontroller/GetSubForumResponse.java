package com.novavrbe.vrbe.models.forumcontroller;

import lombok.Data;

import java.util.ArrayList;

@Data
public class GetSubForumResponse {
    private String forumName;
    private ArrayList<SubForum> subForums;
}

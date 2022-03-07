package com.novavrbe.vrbe.models.forumcontroller;

import lombok.Data;

import java.util.ArrayList;

@Data
public class GetPostCommentResponse {
    private ArrayList<Comment> commentList;
}

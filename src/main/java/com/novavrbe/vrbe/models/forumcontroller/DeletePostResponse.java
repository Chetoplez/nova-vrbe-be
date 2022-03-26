package com.novavrbe.vrbe.models.forumcontroller;

import lombok.Data;

@Data
public class DeletePostResponse {
    private boolean deleted;
    private String message;
}

package com.novavrbe.vrbe.models.forumcontroller;

import lombok.Data;

@Data
public class DeleteSubforumResponse {
    private boolean deleted;
    private String message;
}

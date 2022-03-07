package com.novavrbe.vrbe.models.forumcontroller;

import lombok.Data;

@Data
public class GetSubforumRequest {
    private String chId;
    private String forumId;
}

package com.novavrbe.vrbe.models.forumcontroller;

import lombok.Data;

@Data
public class Forum {
    private String name;

    private boolean adminOnly;

    private boolean adminViewOnly;

    private Integer ownedBy;
}

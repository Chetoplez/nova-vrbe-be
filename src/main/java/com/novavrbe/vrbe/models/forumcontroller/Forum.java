package com.novavrbe.vrbe.models.forumcontroller;

import lombok.Data;

@Data
public class Forum {

    private Integer forumId;

    private String  name;

    private String description;

    private boolean adminOnly;

    private String forumType;

    private Integer ownedBy;

    private Integer visualOrder;

    private boolean unread;
}

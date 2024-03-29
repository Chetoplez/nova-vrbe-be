package com.novavrbe.vrbe.models.forumcontroller;

import lombok.Data;

@Data
public class SubForum {

    private Integer subforumId;

    private String name;

    private boolean adminOnly;

    private Integer forumId;

    private String subforumType;

    private Integer ownedBy;

    private Integer rankVisibility;

    private Integer visualOrder;

    private boolean unread;
}

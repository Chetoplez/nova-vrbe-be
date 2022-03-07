package com.novavrbe.vrbe.models.forumcontroller;

import com.novavrbe.vrbe.models.charactercontroller.SmallCharacter;
import lombok.Data;

@Data
public class Post {

    private Integer postId;

    private String title;

    private String body;

    private Integer forumId;


    private Integer subforumId;

    private SmallCharacter author;


    private boolean isClosed;


    private boolean isPinned;

    private long createdAt;

    private long lastModified;

}

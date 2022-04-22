package com.novavrbe.vrbe.models.forumcontroller;

import com.novavrbe.vrbe.models.charactermodels.SmallCharacter;
import lombok.Data;

@Data
public class Comment {

    private Integer commentId;

    private String body;

    private SmallCharacter author;

    private Integer relatedComment; //referenzia un commento per rispondere

    private Integer postId;

    private long createdAt;
}

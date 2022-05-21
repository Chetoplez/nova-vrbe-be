package com.novavrbe.vrbe.models.forumcontroller;



import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
public class IdPostLetti implements Serializable {
    private Integer chId;
    private Integer postId;

    public IdPostLetti(){}

    @Override
    public boolean equals(Object o){
        // self check
        if (this == o)
            return true;
        // null check
        if (o == null)
            return false;
        // type check and cast
        if (getClass() != o.getClass())
            return false;
        IdPostLetti postLetti = (IdPostLetti) o;
        // field comparison
        return Objects.equals(chId, postLetti.getChId()) && Objects.equals(postId, postLetti.getPostId());
    }
}

package com.novavrbe.vrbe.dto;

import com.novavrbe.vrbe.models.forumcontroller.IdPostLetti;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "POST_LETTI")
@IdClass(IdPostLetti.class)
public class PostLettiDto {

    @Id
    @Column
    private Integer chId;
    @Id
    @Column
    private Integer postId;
    @Column
    private Integer forumId;
    @Column
    private Integer subforumId;
    @Column
    private long lastLettura;
}

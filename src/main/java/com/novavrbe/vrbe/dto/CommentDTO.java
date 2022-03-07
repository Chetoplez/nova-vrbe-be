package com.novavrbe.vrbe.dto;
import lombok.Data;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Data
@Entity
@Table(name = "COMMENTS")
public class CommentDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer commentID;

    @Column
    private String body;

    @Column
    private Integer author;

    @Column
    @Nullable
    private Integer realatedComment; //referenzia un commento per rispondere

    @Column
    private Integer postId;

    @Column
    private long createdAt;

}

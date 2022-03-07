package com.novavrbe.vrbe.dto;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "POSTS")
public class PostDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer postId;

    @Column
    private String title;

    @Column
    private String body;

    @Column
    private Integer forumId;

    @Column
    private Integer subforumId;

    @Column
    private Integer author;

    @Column
    private boolean isClosed;

    @Column
    private boolean isPinned;

    @Column
    private long createdAt;

    @Column
    private long lastModified;

}

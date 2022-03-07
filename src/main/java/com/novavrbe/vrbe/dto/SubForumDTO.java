package com.novavrbe.vrbe.dto;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "SUBFORUMS")
public class SubForumDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer subforumId;

    @Column
    private String name;

    @Column
    private boolean adminOnly;

    @Column
    private Integer forumId;

    @Column
    private Integer rankVisibility;

    @Column
    private String subforumType;

}

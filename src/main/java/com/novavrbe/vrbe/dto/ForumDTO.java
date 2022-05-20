package com.novavrbe.vrbe.dto;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "FORUMS")
public class ForumDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer forumId;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private boolean adminOnly;

    @Column
    private Integer ownedBy;

    @Column
    private String forumType;

    @Column
    private Integer visualOrder;

    @Column
    private long lastModified;
}

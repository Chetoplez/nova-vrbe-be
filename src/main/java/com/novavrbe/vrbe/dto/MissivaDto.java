package com.novavrbe.vrbe.dto;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "Missive")
@Data
public class MissivaDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer missivaId;
    @Column
    private String chTo;
    @Column
    private Integer chFrom;
    @Column
    private String body;
    @Column
    private String subject;
    @Column
    private Long sentAt;
    @Column
    private Long receivedAt;
    @Column
    private boolean isRead;
    @Column
    private boolean deletedTo;
    @Column
    private boolean deletedFrom;
    @Column
    private String type;


}

package com.novavrbe.vrbe.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Missive")
@Data
public class MissivaDto {

    @Id
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
    private boolean deleted;
    @Column
    private String type;


}

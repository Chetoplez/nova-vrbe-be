package com.novavrbe.vrbe.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "SMTPCONFIG")
public class SmtpConfigDto {
    @Id
    @Column
    private Integer idConf;
    @Column
    private String username;
    @Column
    private String password;
}

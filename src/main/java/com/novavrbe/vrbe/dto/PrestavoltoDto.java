package com.novavrbe.vrbe.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "PRESTAVOLTO")
public class PrestavoltoDto {

    @Id
    @Column
    private Integer chId;

    @Column
    private String name;
}

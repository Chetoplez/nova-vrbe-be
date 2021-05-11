package com.novavrbe.vrbe.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "PresentiLuogo")
@Entity
@Data
public class PresentiLuogoDto implements Serializable {
    @Id
    @Column
    private Integer characterId;
    @Column
    private Integer idLuogo;
    @Column
    private boolean available;
    @Column
    private String messaggio;
    @Column
    private boolean online;
}

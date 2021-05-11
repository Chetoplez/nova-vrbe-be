package com.novavrbe.vrbe.dto;

import lombok.CustomLog;
import lombok.Data;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Immutable
@Entity
public class V_Presenti {

    @Column
    private Integer idLuogo;
    @Column
    private String nomeLuogo;
    @Id
    @Column
    private String characterName;
    @Column
    private Integer characterId;
    @Column
    private String characterImg;
    @Column
    private String messaggio;
    @Column
    private boolean available;
    @Column
    private boolean online;

}

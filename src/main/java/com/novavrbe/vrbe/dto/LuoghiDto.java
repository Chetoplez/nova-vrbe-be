package com.novavrbe.vrbe.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Luoghi")
@Data
public class LuoghiDto {
    @Id
    private Integer idLuogo;
    @Column
    private String descr;
    @Column
    private String nomeLuogo;
    @Column
    private String statoLuogo;
    @Column
    private String immagine;
}

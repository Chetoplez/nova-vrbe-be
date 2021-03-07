package com.novavrbe.vrbe.dto;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "Luoghi")
@Data
public class LuoghiDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

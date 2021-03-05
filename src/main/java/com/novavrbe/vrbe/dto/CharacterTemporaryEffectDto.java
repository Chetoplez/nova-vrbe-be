package com.novavrbe.vrbe.dto;

import lombok.Data;

import javax.persistence.*;

@Table(name = "CharacterTemporaryEffect")
@Data
@Entity
public class CharacterTemporaryEffectDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private Integer characterId;
    @Column
    private Integer modifier;
    @Column
    private String stat;
}

package com.novavrbe.vrbe.dto;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "PresentiLuogo")
@Entity
@Data
public class PresentiLuogoDto implements Serializable {
    @Id
    private Integer characterId;
    @Id
    private Integer idLuogo;
}

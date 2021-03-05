package com.novavrbe.vrbe.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "CharactersHistory")
@Data
@Entity
public class CharacterHistoryDto {
    @Id
    private Integer historyId;
    @Column
    private String history;
}

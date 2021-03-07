package com.novavrbe.vrbe.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Chat")
@Data
public class ChatDto {

    @Id
    private String chatId;
    @Column
    private String creationDate;
    @Column
    private Boolean active;
    @Column
    private Integer idLuogo;
    @Column
    private Boolean privateChat;
    @Column
    private Integer characterId;
    @Column
    private String expirationDate;

}

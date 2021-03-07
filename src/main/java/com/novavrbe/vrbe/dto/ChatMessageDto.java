package com.novavrbe.vrbe.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "ChatMessages")
@Entity
@Data
public class ChatMessageDto {
    @Id
    private String id;
    @Column
    private String action;
    @Column
    private String carica;
    @Column
    private String idAzione;
    @Column
    private String img;
    @Column
    private String receiver;
    @Column
    private String sender;
    @Column
    private String tag;
    @Column
    private String testo;
    @Column
    private Date timestamp;
    @Column
    private String tooltip_carica;
}

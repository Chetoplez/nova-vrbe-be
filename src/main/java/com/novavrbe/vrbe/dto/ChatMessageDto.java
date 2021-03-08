package com.novavrbe.vrbe.dto;

import com.novavrbe.vrbe.models.enumerations.ChatAction;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Table(name = "ChatMessages")
@Entity
@Data
public class ChatMessageDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private Integer chatId;
    @Column (name = "cAction")
    private String action;
    @Column
    private String carica;
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
    @Column (name = "cTimestamp")
    private Date timestamp;
    @Column
    private String tooltip_carica;
}

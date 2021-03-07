package com.novavrbe.vrbe.models.chatcontroller;

import lombok.Data;

import java.util.Date;

@Data
public class ChatMessage {
    private String id;
    private String action;
    private String carica;
    private String idAzione;
    private String img;
    private String receiver;
    private String sender;
    private String tag;
    private String testo;
    private Date timestamp;
    private String tooltip_carica;
}

package com.novavrbe.vrbe.models.chatcontroller;

import com.novavrbe.vrbe.models.enumerations.ChatAction;
import lombok.Data;

import java.util.Date;

@Data
public class ChatMessage {
    private String id;
    private String action;
    private String carica;
    private String img;
    private String receiver;
    private String sender;
    private String tag;
    private String testo;
    private Date timestamp;
    private String tooltip_carica;
}

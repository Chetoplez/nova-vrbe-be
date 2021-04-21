package com.novavrbe.vrbe.models.chatcontroller;

import lombok.Data;

@Data
public class AttackRequest {
    private String chatId;
    private String attackerId;
    private String defenderId;
    private String img;
    private String sender;
    private String defernderName;
    private String tag;
    private String carica;

}

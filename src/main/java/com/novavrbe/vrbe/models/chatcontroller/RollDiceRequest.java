package com.novavrbe.vrbe.models.chatcontroller;

import lombok.Data;

@Data
public class RollDiceRequest {
    private String chatId;
    private String charachterId;
    private String statName;
    private String img;
    private String sender;
    private String tag;
    private String carica;
}

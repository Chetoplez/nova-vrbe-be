package com.novavrbe.vrbe.models.chatcontroller;

import lombok.Data;

@Data
public class DeleteMessageRequest {
    private String chatId;
    private String messageId;}

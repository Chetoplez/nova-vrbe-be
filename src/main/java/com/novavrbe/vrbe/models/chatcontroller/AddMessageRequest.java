package com.novavrbe.vrbe.models.chatcontroller;

import lombok.Data;

@Data
public class AddMessageRequest {
    private String chatId;
    private ChatMessage chatMessage;
}

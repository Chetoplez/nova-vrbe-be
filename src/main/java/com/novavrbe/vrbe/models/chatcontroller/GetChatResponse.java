package com.novavrbe.vrbe.models.chatcontroller;

import lombok.Data;

import java.util.List;
@Data
public class GetChatResponse {
   private List<ChatMessage> chatMessageList;
}

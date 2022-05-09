package com.novavrbe.vrbe.models.chatcontroller;

import lombok.Data;

import java.util.ArrayList;

@Data
public class GetChatListResponse {
    private ArrayList<Chat> chatList;
}

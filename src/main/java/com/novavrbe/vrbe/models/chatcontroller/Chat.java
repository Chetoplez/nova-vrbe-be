package com.novavrbe.vrbe.models.chatcontroller;

import lombok.Data;

@Data
public class Chat {
    private String nomeChat;
    private Integer idChat;
    private Integer pos_x;
    private Integer pos_y;
    private boolean privateChat;

}

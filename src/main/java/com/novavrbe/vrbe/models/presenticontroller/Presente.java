package com.novavrbe.vrbe.models.presenticontroller;

import lombok.Data;

@Data
public class Presente {
    private String characterName;
    private Integer characterId;
    private String characterImg;
    private boolean available;
    private String messaggio;
}
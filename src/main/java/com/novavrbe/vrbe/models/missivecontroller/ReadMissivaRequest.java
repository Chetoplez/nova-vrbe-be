package com.novavrbe.vrbe.models.missivecontroller;

import lombok.Data;

import java.util.ArrayList;

@Data
public class ReadMissivaRequest {
    private String chId;
    private ArrayList<Integer> idMissive;
}

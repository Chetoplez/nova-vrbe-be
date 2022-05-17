package com.novavrbe.vrbe.models.missivecontroller;

import lombok.Data;

import java.util.ArrayList;

@Data
public class DeleteMissiveRequest {
    private String chId;
    private ArrayList<Integer> idMissive;
    private boolean inbox;
}

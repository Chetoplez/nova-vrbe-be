package com.novavrbe.vrbe.models.missivecontroller;

import lombok.Data;

import java.util.ArrayList;

@Data
public class GetMissiveResponse {
    private ArrayList<Missiva> missiveList;

    public GetMissiveResponse (){
        this.missiveList = new ArrayList<>();
    }

}

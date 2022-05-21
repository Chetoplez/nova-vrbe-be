package com.novavrbe.vrbe.models.charactercontroller;

import com.novavrbe.vrbe.models.charactermodels.Prestavolto;
import lombok.Data;

import java.util.ArrayList;

@Data
public class GetPrestavoltoResponse {
    private ArrayList<Prestavolto> prestavoltoList;

    public GetPrestavoltoResponse(){
        this.prestavoltoList = new ArrayList<>();
    }
}

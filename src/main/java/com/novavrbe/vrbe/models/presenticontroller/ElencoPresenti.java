package com.novavrbe.vrbe.models.presenticontroller;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ElencoPresenti {
    private Integer idLuogo;
    private String nomeLuogo;
    private List<Presente> presenteList = new ArrayList<>();
}

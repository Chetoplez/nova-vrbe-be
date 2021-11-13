package com.novavrbe.vrbe.models.charactercontroller;

import lombok.Data;

import java.util.ArrayList;

@Data
public class GetListUnemployedResponse {
    private ArrayList<SmallCharacter> unemployed;
}

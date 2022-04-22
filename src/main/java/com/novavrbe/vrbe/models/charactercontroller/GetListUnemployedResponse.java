package com.novavrbe.vrbe.models.charactercontroller;

import com.novavrbe.vrbe.models.charactermodels.SmallCharacter;
import lombok.Data;

import java.util.ArrayList;

@Data
public class GetListUnemployedResponse {
    private ArrayList<SmallCharacter> unemployed;
}

package com.novavrbe.vrbe.models.charactermodels;

import lombok.Data;

@Data
public class User extends GenericUser {
    private Character character;
}

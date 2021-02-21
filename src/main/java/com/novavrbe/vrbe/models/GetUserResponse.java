package com.novavrbe.vrbe.models;

import com.novavrbe.vrbe.models.charactermodels.User;
import lombok.Data;

@Data
public class GetUserResponse {
    private User user;
}

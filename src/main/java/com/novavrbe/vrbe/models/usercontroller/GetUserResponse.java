package com.novavrbe.vrbe.models.usercontroller;

import com.novavrbe.vrbe.models.charactermodels.GenericUser;
import lombok.Data;

@Data
public class GetUserResponse {
    private GenericUser user;
}

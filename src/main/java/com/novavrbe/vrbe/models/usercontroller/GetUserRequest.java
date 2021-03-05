package com.novavrbe.vrbe.models.usercontroller;

import com.novavrbe.vrbe.models.charactermodels.UserDtoPojo;
import lombok.Data;

@Data
public class GetUserRequest {
    private UserDtoPojo userPojo;
}

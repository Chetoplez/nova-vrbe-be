package com.novavrbe.vrbe.models.usercontroller;

import com.novavrbe.vrbe.models.charactermodels.UserPojo;
import lombok.Data;

@Data
public class GetUserRequest {
    private UserPojo userPojo;
}
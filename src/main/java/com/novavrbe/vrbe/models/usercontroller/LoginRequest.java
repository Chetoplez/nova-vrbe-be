package com.novavrbe.vrbe.models.usercontroller;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String psw;
}

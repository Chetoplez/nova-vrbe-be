package com.novavrbe.vrbe.models.usercontroller;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class LoginResponse {
    private boolean success;
    private BigDecimal userId;
    private String role;
    private  String jwt;
}

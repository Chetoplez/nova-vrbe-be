package com.novavrbe.vrbe.models.missivecontroller;

import lombok.Data;

@Data
public class SendMissiveResponse {
    private boolean sent;
    private String message;
}

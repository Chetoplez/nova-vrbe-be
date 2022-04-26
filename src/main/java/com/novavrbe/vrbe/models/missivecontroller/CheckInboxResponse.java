package com.novavrbe.vrbe.models.missivecontroller;

import lombok.Data;

@Data
public class CheckInboxResponse {
    private boolean newMail;
    private Integer nNewMail;
}

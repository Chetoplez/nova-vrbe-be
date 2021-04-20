package com.novavrbe.vrbe.models.guildcontroller;

import lombok.Data;

@Data
public class OperationBankRequest {

    private String guildId;
    private String characterId;
    private String amount;

}

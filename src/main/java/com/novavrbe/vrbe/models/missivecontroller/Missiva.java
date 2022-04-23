package com.novavrbe.vrbe.models.missivecontroller;

import com.novavrbe.vrbe.models.charactermodels.SmallCharacter;
import lombok.Data;

@Data
public class Missiva {

    private Integer missivaId;

    private SmallCharacter to;

    private SmallCharacter from;

    private String body;

    private String subject;

    private Long sentAt;

    private Long receivedAt;

    private boolean isRead;

    private String type;

}

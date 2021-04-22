package com.novavrbe.vrbe.models.guildcontroller;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class CharacterCv {
    private String img;
    private String roleName;

    private Date enrolmentDate;

}

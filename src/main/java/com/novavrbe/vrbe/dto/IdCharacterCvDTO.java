package com.novavrbe.vrbe.dto;

import lombok.Data;

import java.io.Serializable;
import java.security.KeyStore;
import java.util.Date;

@Data
public class IdCharacterCvDTO implements Serializable {
    private Integer characterId;
    private Integer roleId;
    private Date enrollmentDate;

}

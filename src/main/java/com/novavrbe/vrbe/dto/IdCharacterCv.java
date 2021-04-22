package com.novavrbe.vrbe.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

@Data
public class IdCharacterCv implements Serializable {

    private Integer characterId;
    private Date enrollmentDate;


    public IdCharacterCv(){}

    @Override
    public boolean equals(Object o){
        // self check
        if (this == o)
            return true;
        // null check
        if (o == null)
            return false;
        // type check and cast
        if (getClass() != o.getClass())
            return false;
        IdCharacterCv characterCv = (IdCharacterCv) o;
        // field comparison
        return Objects.equals(enrollmentDate, characterCv.getEnrollmentDate()) && Objects.equals(characterId, characterCv.getCharacterId());
    }


}

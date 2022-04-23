package com.novavrbe.vrbe.utils;

import com.novavrbe.vrbe.dto.CharacterDto;
import com.novavrbe.vrbe.dto.MissivaDto;
import com.novavrbe.vrbe.models.charactermodels.SmallCharacter;
import com.novavrbe.vrbe.models.missivecontroller.Missiva;

public class MissivaUtils {

    public static Missiva prepareMissivafromDto(MissivaDto dto, CharacterDto fromDto, CharacterDto toDto) {
        Missiva temp = new Missiva();
        SmallCharacter from = new SmallCharacter();
        SmallCharacter to = new SmallCharacter();

        from.setCharacterId(fromDto.getCharacterId());
        from.setCharacterName(fromDto.getCharacterName());
        from.setCharacterSurname(fromDto.getCharacterSurname());
        from.setCharacterImg(fromDto.getCharacterImg());

        to.setCharacterId(toDto.getCharacterId());
        to.setCharacterName(toDto.getCharacterName());
        to.setCharacterSurname(toDto.getCharacterSurname());
        to.setCharacterImg(toDto.getCharacterImg());
        temp.setTo(to);

        temp.setFrom(from);
        temp.setMissivaId(dto.getMissivaId());
        temp.setBody(dto.getBody());
        temp.setRead(dto.isRead());
        temp.setReceivedAt(dto.getReceivedAt());
        temp.setSentAt(dto.getSentAt());
        temp.setSubject(dto.getSubject());
        temp.setType(dto.getType());

        return temp;
    }

    public static MissivaDto prepareMissivaDto(Missiva missiva) {
        MissivaDto dto = new MissivaDto();

        dto.setBody(missiva.getBody());
        dto.setSubject(missiva.getSubject());
        dto.setType(missiva.getType());
        dto.setRead(false);
        dto.setChFrom(missiva.getFrom().getCharacterId());
        dto.setChTo(missiva.getTo().getCharacterId().toString());
        dto.setSentAt(missiva.getSentAt());
        dto.setReceivedAt(missiva.getReceivedAt());
        dto.setDeleted(false);
        return dto;

    }
}

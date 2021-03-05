package com.novavrbe.vrbe.models.charactermodels;

import com.novavrbe.vrbe.dto.GenericUserDto;
import lombok.Data;

@Data
public class UserDto extends GenericUserDto {
    private Character character;
}

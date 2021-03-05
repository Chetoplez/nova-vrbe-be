package com.novavrbe.vrbe.models.usercontroller;

import com.novavrbe.vrbe.dto.GenericUserDto;
import lombok.Data;

@Data
public class GetUserResponse {
    private GenericUserDto user;
}

package com.novavrbe.vrbe.models.guildcontroller;

import com.novavrbe.vrbe.dto.CharacterCvDTO;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CharacterCvResponse {
    private List<CharacterCv> curruculumPg;
}

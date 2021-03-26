package com.novavrbe.vrbe.business;


import com.novavrbe.vrbe.dto.GuildBankDTO;
import com.novavrbe.vrbe.dto.GuildDTO;
import com.novavrbe.vrbe.models.guildcontroller.GetGuildResponse;
import com.novavrbe.vrbe.models.guildcontroller.Guild;
import com.novavrbe.vrbe.repositories.impl.GuildBankRepositoryService;
import com.novavrbe.vrbe.repositories.impl.GuildRepositoryService;
import com.novavrbe.vrbe.utils.GuildUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class GuildBusiness {

    @Autowired
    private GuildRepositoryService guildRepositoryService;
    @Autowired
    private GuildBankRepositoryService guildBankRepositoryService;

    public ResponseEntity<GetGuildResponse> getGuild(String guildId) {
        ResponseEntity<GetGuildResponse> response;
        Guild guild;
        GuildDTO guildDTO;
        GuildBankDTO guildBankDTO;

        if(!StringUtils.hasText(guildId) ){
            response = new ResponseEntity<>(new GetGuildResponse(), HttpStatus.BAD_REQUEST);
            return response;
        }
        guildDTO = guildRepositoryService.getGuildById(Integer.parseInt(guildId));
        guildBankDTO = guildBankRepositoryService.getGuildBank(Integer.parseInt(guildId));
        if(guildDTO != null && guildBankDTO != null){
            guild = GuildUtils.createGuildObject(guildDTO,guildBankDTO);
            GetGuildResponse guildResponse = new GetGuildResponse();
            guildResponse.setGuild(guild);
            response = new ResponseEntity<>(guildResponse, HttpStatus.OK);
        }else {
            response = new ResponseEntity<>(new GetGuildResponse(), HttpStatus.BAD_REQUEST);
        }
        return response;
    }


}

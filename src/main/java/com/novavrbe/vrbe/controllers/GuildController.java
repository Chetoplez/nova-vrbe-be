package com.novavrbe.vrbe.controllers;
import com.novavrbe.vrbe.business.GuildBusiness;
import com.novavrbe.vrbe.models.guildcontroller.GetGuildResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/guild")
public class GuildController {

    @Autowired
    private GuildBusiness guildBusiness;

    @GetMapping("/idguild={guildId}")
    public  ResponseEntity<GetGuildResponse> getGuild(@PathVariable String guildId){
        return guildBusiness.getGuild(guildId);
    }
}

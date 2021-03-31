package com.novavrbe.vrbe.controllers;
import com.novavrbe.vrbe.business.GuildBusiness;
import com.novavrbe.vrbe.models.guildcontroller.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/guild")
public class GuildController {

    @Autowired
    private GuildBusiness guildBusiness;

    @GetMapping("/guildid={guildId}")
    public  ResponseEntity<GetGuildResponse> getGuild(@PathVariable String guildId){
        return guildBusiness.getGuild(guildId);
    }

    @GetMapping("/role/guildid={guildId}")
    public ResponseEntity<GetGuildRoleReponse> getGuildRole(@PathVariable String guildId){
        return guildBusiness.getGuildRole(guildId);
    }

    @GetMapping("/members/guildid={guildId}")
    public ResponseEntity<GetGuildMemberListDTOResponse> getGuildMembers(@PathVariable String guildId){
        return guildBusiness.getGuildMembers(guildId);
    }

    @PostMapping("/members/addmember")
    public ResponseEntity<AddMemberResponse> addMember(@RequestBody AddMemberRequest addMemberRequest){
    return guildBusiness.addMember(addMemberRequest);
    }

    @PatchMapping("/member/promote")
    public ResponseEntity<PromoteMemberResponse> promoteMember(@RequestBody PromoteMemberRequest promoteMemberRequest){
        return guildBusiness.promoteGuildMember(promoteMemberRequest);
    }
}

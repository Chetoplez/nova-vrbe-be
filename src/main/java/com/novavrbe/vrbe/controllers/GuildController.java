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
    public ResponseEntity<GetGuildMemberResponse> getGuildMembers(@PathVariable String guildId){
        return guildBusiness.getGuildMembers(guildId);
    }

    @PostMapping("/members/addmember")
    public ResponseEntity<AddMemberResponse> addMember(@RequestBody AddMemberRequest addMemberRequest){
    return guildBusiness.addMember(addMemberRequest);
    }

    @DeleteMapping("/members/deletemember")
    public ResponseEntity<DeleteMemberResponse> deleteMember(@RequestBody DeleteMemberRequest deleteMemberRequest){
        return guildBusiness.deleteMember(deleteMemberRequest);
    }

    @PatchMapping("/members/promote")
    public ResponseEntity<PromoteMemberResponse> promoteMember(@RequestBody PromoteMemberRequest promoteMemberRequest){
        return guildBusiness.promoteGuildMember(promoteMemberRequest);
    }

    @PatchMapping("/members/degradate")
    public ResponseEntity<DegradeMemberResponse> degradadeMember(@RequestBody DegradateMemberRequest degradeRequest){
        return guildBusiness.degradeGuildMember(degradeRequest);
    }

    @PostMapping("/members/checkguildPermission/")
    public ResponseEntity<CheckGuildPermissionResponse> checkGuildPermission(@RequestBody CheckGuildPermissionRequest request){
        return guildBusiness.checkGuildPermission(request);
    }
}

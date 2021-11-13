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

    @GetMapping("/getall")
    public  ResponseEntity<GetAllGuildResponse> getGuilds(){
        return guildBusiness.getAllGuilds();
    }

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


    @GetMapping("/members/getinfo={chId}")
    public ResponseEntity<GetinfoRoleResponse> getInfoRole (@PathVariable String chId){
        return guildBusiness.getInfoRole(chId);
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

    @GetMapping("/members/getcharactercv/characterId={characterId}")
    public ResponseEntity<CharacterCvResponse> getCharacterCv(@PathVariable String characterId){
        return guildBusiness.getCharacterCv(characterId);
    }

    @PostMapping("/members/checkguildpermission")
    public ResponseEntity<CheckGuildPermissionResponse> checkGuildPermission(@RequestBody CheckGuildPermissionRequest request){
        return guildBusiness.checkGuildPermission(request);
    }

    @PatchMapping("/members/withdraw")
    public ResponseEntity<OperationBankResponse> withdraw(@RequestBody OperationBankRequest request){
        return guildBusiness.withdraw(request);
    }

    @PatchMapping("/members/deposit")
    public ResponseEntity<OperationBankResponse> deposit(@RequestBody OperationBankRequest request){
        return guildBusiness.deposit(request);
    }

    @PostMapping("/members/getsalary")
    public ResponseEntity<GetSalaryResponse> getSalary(@RequestBody GetSalaryRequest request){
        return guildBusiness.getSalary(request);
    }

}

package com.novavrbe.vrbe.business;


import com.novavrbe.vrbe.dto.GuildBankDTO;
import com.novavrbe.vrbe.dto.GuildDTO;
import com.novavrbe.vrbe.dto.GuildMemberListDTO;
import com.novavrbe.vrbe.dto.GuildRoleDTO;
import com.novavrbe.vrbe.models.guildcontroller.*;
import com.novavrbe.vrbe.repositories.impl.GuildBankRepositoryService;
import com.novavrbe.vrbe.repositories.impl.GuildRepositoryService;
import com.novavrbe.vrbe.repositories.impl.GuildRoleRepositoryService;
import com.novavrbe.vrbe.utils.GuildUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class GuildBusiness {

    @Autowired
    private GuildRepositoryService guildRepositoryService;
    @Autowired
    private GuildBankRepositoryService guildBankRepositoryService;
    @Autowired
    private GuildRoleRepositoryService guildRoleRepositoryService;

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

    public ResponseEntity<GetGuildRoleReponse> getGuildRole(String guildId){
        ResponseEntity<GetGuildRoleReponse> response;
        if(!StringUtils.hasText(guildId)){
            response = new ResponseEntity<>(new GetGuildRoleReponse(), HttpStatus.BAD_REQUEST);
            return response;
        }

        List<GuildRoleDTO> roleDTO = guildRoleRepositoryService.getRoleByGuildId(Integer.parseInt(guildId));
        List<GuildRole> guildRoleList =  GuildUtils.prepareGuildRoles(roleDTO);
        GetGuildRoleReponse res = new GetGuildRoleReponse();
        res.setGuildRoleList(guildRoleList);
        response = new ResponseEntity<>(res, HttpStatus.OK);

    return  response;
    }

    /**
     * Torna i membri di una gilda
     * @param guildId L'id della gilda
     * @return
     */
    public ResponseEntity<GetGuildMemberListDTOResponse> getGuildMembers(String guildId){
        ResponseEntity<GetGuildMemberListDTOResponse> response;
        if(!StringUtils.hasText(guildId)){
            response = new ResponseEntity<>(new GetGuildMemberListDTOResponse(), HttpStatus.BAD_REQUEST);
            return response;
        }

        List<GuildMemberListDTO> members = guildRepositoryService.getGuildMembers(Integer.parseInt(guildId));
        GetGuildMemberListDTOResponse res = new GetGuildMemberListDTOResponse();
        res.setMembers(members);
        response = new ResponseEntity<>(res,HttpStatus.OK);
        return  response;
    }

    /**
     * Inserisce un personaggio in una gilda, solo se è DISOCCUPATO
     * @param addMemberRequest
     * @return TRUE se viene inserito, false in tutti gli altri casi
     */
    public ResponseEntity<AddMemberResponse> addMember(AddMemberRequest addMemberRequest){
        ResponseEntity<AddMemberResponse> response = null;
        String characterId,roleId;
        characterId = addMemberRequest.getCharacter_id();
        roleId = addMemberRequest.getRole_id();
        if(!StringUtils.hasText(roleId) || !StringUtils.hasText(characterId)){
            response = new ResponseEntity<>(new AddMemberResponse(), HttpStatus.BAD_REQUEST);
            return response;
        }
        //vedo se il pg è già dentro una gilda, come? lo cerco tra i guildMembers!
        if(!guildRepositoryService.checkGuildPresence(Integer.parseInt(characterId))){
            //Se sono qui dentro, il pg non è arruolato da nessuna parte.
            AddMemberResponse res = new AddMemberResponse();
            res.setAdded(guildRepositoryService.addMember(Integer.parseInt(roleId), Integer.parseInt(characterId)));
            response = new ResponseEntity<>(res,HttpStatus.OK);
        }else {
            AddMemberResponse res = new AddMemberResponse();
            res.setAdded(false);
            response = new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
        }
        return response;
    }

}

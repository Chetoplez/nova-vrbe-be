package com.novavrbe.vrbe.business;


import com.novavrbe.vrbe.dto.GuildBankDTO;
import com.novavrbe.vrbe.dto.GuildDTO;
import com.novavrbe.vrbe.dto.GuildMemberListDTO;
import com.novavrbe.vrbe.dto.GuildRoleDTO;
import com.novavrbe.vrbe.models.guildcontroller.*;
import com.novavrbe.vrbe.repositories.impl.GuildRepositoryService;
import com.novavrbe.vrbe.utils.GuildUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class GuildBusiness {

    //TODO aggiungere a tutti i metodi gestionali l'ID dell character che sta eseguendo,in modo da verificare che possegga questo diritto.

    @Autowired
    private GuildRepositoryService guildRepositoryService;

    /**
     * Torna le informazioni della gilda
     * @param guildId L'id della gilda
     * @return GuildResponse contenente tutto quello che concerne la gilda
     */
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
        guildBankDTO = guildRepositoryService.getGuildBank(Integer.parseInt(guildId));
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

    /**
     * Torna i ruoli di una determinata gilda con tutti i loro attributi
     * @param guildId l'id della gilda per cui si vuole la gerarchia
     * @return una lista dei ruoli con i loro attributi
     */
    public ResponseEntity<GetGuildRoleReponse> getGuildRole(String guildId){
        ResponseEntity<GetGuildRoleReponse> response;
        if(!StringUtils.hasText(guildId)){
            response = new ResponseEntity<>(new GetGuildRoleReponse(), HttpStatus.BAD_REQUEST);
            return response;
        }

        List<GuildRoleDTO> roleDTO = guildRepositoryService.getRoleByGuildId(Integer.parseInt(guildId));
        List<GuildRole> guildRoleList =  GuildUtils.prepareGuildRoles(roleDTO);
        GetGuildRoleReponse res = new GetGuildRoleReponse();
        res.setGuildRoleList(guildRoleList);
        response = new ResponseEntity<>(res, HttpStatus.OK);

    return  response;
    }

    /**
     * Torna i membri di una gilda
     * @param guildId L'id della gilda
     * @return I membri di una gilda con le info.
     */
    public ResponseEntity<GetGuildMemberResponse> getGuildMembers(String guildId){
        ResponseEntity<GetGuildMemberResponse> response;
        List<GuildMember> members = new ArrayList<>();
        if(!StringUtils.hasText(guildId)){
            response = new ResponseEntity<>(new GetGuildMemberResponse(), HttpStatus.BAD_REQUEST);
            return response;
        }
        List<GuildMemberListDTO> membersDTO = guildRepositoryService.getGuildMembers(Integer.parseInt(guildId));
        GetGuildMemberResponse res = new GetGuildMemberResponse();
        for (GuildMemberListDTO tmp: membersDTO) {
            GuildMember newMember = GuildUtils.getMemberfromDTO(tmp);
            members.add(newMember);
        }

        res.setMembers(members);
        response = new ResponseEntity<>(res,HttpStatus.OK);
        return  response;
    }

    /**
     * Inserisce un personaggio in una gilda, solo se è DISOCCUPATO
     * @param addMemberRequest oggetto contentente i parametri per pruomuovere un character. il suo iD e l'ìd della gilda
     * @return TRUE se viene inserito, false in tutti gli altri casi
     */
    public ResponseEntity<AddMemberResponse> addMember(AddMemberRequest addMemberRequest){
        ResponseEntity<AddMemberResponse> response;
        String characterId,roleId;
        characterId = addMemberRequest.getCharacter_id();
        roleId = addMemberRequest.getRole_id();
        if(!StringUtils.hasText(roleId) || !StringUtils.hasText(characterId)){
            response = new ResponseEntity<>(new AddMemberResponse(), HttpStatus.BAD_REQUEST);
            return response;
        }
        //vedo se il pg è già dentro una gilda, come? lo cerco tra i guildMembers!
        if(!guildRepositoryService.checkEnrollment(Integer.parseInt(characterId))){
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

    public ResponseEntity<DeleteMemberResponse> deleteMember(DeleteMemberRequest deleteMemberRequest){
        ResponseEntity<DeleteMemberResponse> response ;
        if(!StringUtils.hasText(deleteMemberRequest.getCharacter_id()) || !StringUtils.hasText(deleteMemberRequest.getRole_id())){
            response = new ResponseEntity<>(new DeleteMemberResponse(), HttpStatus.BAD_REQUEST);
            return response;
        }
        Integer roleId = Integer.parseInt(deleteMemberRequest.getRole_id());
        Integer cId = Integer.parseInt(deleteMemberRequest.getCharacter_id());
        guildRepositoryService.deleteMember(roleId,cId);
        DeleteMemberResponse res = new DeleteMemberResponse();
        res.setDeleted(!guildRepositoryService.checkEnrollment(cId));
        response = new ResponseEntity<>(res,HttpStatus.OK);
        return response;
    }

    /**
     * Promuove un pg nella scala gerarchica della sua Gilda.
     * @param promoteMember richiesta contenente l'id del character e il l'id della gilda.
     * @return ritorna true se è stato promosso con successo, false altrimenti
     */
    public ResponseEntity<PromoteMemberResponse> promoteGuildMember(PromoteMemberRequest promoteMember){
        ResponseEntity<PromoteMemberResponse> response;
        if(!StringUtils.hasText(promoteMember.getGuild_id()) || !StringUtils.hasText(promoteMember.getCharacter_id())){
            response = new ResponseEntity<>(new PromoteMemberResponse(), HttpStatus.BAD_REQUEST);
            return response;
        }
        Integer character_id = Integer.parseInt(promoteMember.getCharacter_id());
        Integer guildId = Integer.parseInt(promoteMember.getGuild_id());
        if(guildRepositoryService.promoteMember(character_id)){
            PromoteMemberResponse res = new PromoteMemberResponse();
            res.setPromoted(true);
            response = new ResponseEntity<>(res, HttpStatus.OK);
        }else {
            PromoteMemberResponse res = new PromoteMemberResponse();
            res.setPromoted(false);
            response = new ResponseEntity<>(res, HttpStatus.OK);
        }
        return response;
    }


    /**
     * Degrada nella scala gerarchica della gilda di appartenenza un membro
     * @param degradeMemberRequest richiesta formata da roleId e Character id del membro da degradare. Non espelle.
     * @return true se è stato degradato con successo, false altrimenti
     */
    public ResponseEntity<DegradeMemberResponse> degradeGuildMember(DegradateMemberRequest degradeMemberRequest) {
        ResponseEntity<DegradeMemberResponse> response;
        if(!StringUtils.hasText(degradeMemberRequest.getGuild_id()) || !StringUtils.hasText(degradeMemberRequest.getCharacter_id())){
            response = new ResponseEntity<>(new DegradeMemberResponse(), HttpStatus.BAD_REQUEST);
            return response;
        }
        Integer character_id = Integer.parseInt(degradeMemberRequest.getCharacter_id());
        Integer guildId = Integer.parseInt(degradeMemberRequest.getGuild_id());
        if(guildRepositoryService.degradeMember(character_id)){
            DegradeMemberResponse res = new DegradeMemberResponse();
            res.setDegradated(true);
            response = new ResponseEntity<>(res, HttpStatus.OK);
        }else {
            DegradeMemberResponse res = new DegradeMemberResponse();
            res.setDegradated(false);
            response = new ResponseEntity<>(res, HttpStatus.OK);
        }
        return response;
    }



    public ResponseEntity<CheckGuildPermissionResponse> checkGuildPermission(CheckGuildPermissionRequest request) {

        ResponseEntity<CheckGuildPermissionResponse> response;
        if(!StringUtils.hasText(request.getCharacterId()) || !StringUtils.hasText(request.getGuildId())){
            response = new ResponseEntity<>(new CheckGuildPermissionResponse(),HttpStatus.BAD_REQUEST);
            return response;
        }
        Integer charachterId = Integer.parseInt(request.getCharacterId());
        Integer guildId = Integer.parseInt(request.getGuildId());

        GuildPermission perm = guildRepositoryService.checkGuildPermission(charachterId,guildId);
        CheckGuildPermissionResponse res = new CheckGuildPermissionResponse();
        res.setManager(perm.isManager());
        res.setPresent(perm.isPresente());
        response = new ResponseEntity<>(res, HttpStatus.OK);
        return response;


    }


    /**
     * Controlla se chi sta effettuando l'azione, ne ha il privilegio.
     * @param executorId il characterId di chi ha chiamato il metodo
     * @return true se sei il manager di quella gilda, false altrimenti
     */

    //TODO da implementare.
    private boolean hasManagerRight(Integer executorId){
        return false;
    }
}
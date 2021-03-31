package com.novavrbe.vrbe.repositories.impl;

import com.novavrbe.vrbe.dto.GuildDTO;
import com.novavrbe.vrbe.dto.GuildMemberDTO;
import com.novavrbe.vrbe.dto.GuildMemberListDTO;
import com.novavrbe.vrbe.dto.GuildRoleDTO;
import com.novavrbe.vrbe.repositories.GuildMemberListRepository;
import com.novavrbe.vrbe.repositories.GuildMemeberRepository;
import com.novavrbe.vrbe.repositories.GuildRepository;
import com.novavrbe.vrbe.repositories.GuildRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GuildRepositoryService {

    @Autowired
    private GuildRepository guildRepository;

    @Autowired
    private GuildMemberListRepository guildMember;

    @Autowired
    private  GuildMemeberRepository memberRepo;

    @Autowired
    private GuildRoleRepository roleRepository;

    /**
     * Torna le informazioni della gilda dato il suo id
     * @param guildId id della gilda
     * @return GuildDTO contenente le info della gilda
     */
    public GuildDTO getGuildById(Integer guildId) {
        GuildDTO guildDTO = null;
        if (guildId != null) {
            Optional<GuildDTO> dto = guildRepository.findById(guildId);
            guildDTO = dto.orElse(null);
        }

        return guildDTO;
    }

    /**
     * Torna i membri appartenenenti alla gilda
     * @param guildId L'id della gilda di cui vuoi i conoscere i membri
     * @return Lista dei membri GuildMemberListDTO
     */
    public List<GuildMemberListDTO> getGuildMembers(Integer guildId){
        List<GuildMemberListDTO> members = new ArrayList<>();
        if(guildId != null){
            members = guildMember.findMembers(guildId);
        }
        return members;
    }

    public boolean checkGuildPresence(Integer charachaterId) {
        boolean isPresente;
        Optional<GuildMemberDTO> member = memberRepo.findById(charachaterId);
        isPresente = member.isPresent();
        return isPresente;
    }

    public boolean addMember(Integer roleid, Integer characterId) {
        GuildMemberDTO newMember = new GuildMemberDTO();
        boolean saved = false;
        if(roleid != null && characterId != null){
            newMember.setCHARACTER_ID(characterId);
            newMember.setROLE_ID(roleid);
            GuildMemberDTO savedMember = memberRepo.save(newMember);
            saved = true;
        }

        return saved;
    }

    public boolean promoteMember(Integer characterId){
        boolean promoted = false;
        //intanto mi becco il ruolo ricoperto dal pg
        Optional<GuildMemberDTO> member = memberRepo.findById(characterId);
        Integer role_id;
        role_id = member.map(GuildMemberDTO::getROLE_ID).orElse(null);
        Optional<GuildRoleDTO> dto = (role_id != null) ? roleRepository.findById(role_id) : Optional.empty();
        if(dto.isPresent()){
        List<GuildRoleDTO> listofpossibile = roleRepository.getPossibleNextRoles(dto.get().getGuild_id(),dto.get().getGuild_level());
        if(listofpossibile.size() > 0){
        Integer roleLevel = 2000;
        Integer newRoleid = 0;
        for (GuildRoleDTO temp: listofpossibile) {
            if(temp.getGuild_level() < roleLevel){
                roleLevel = temp.getGuild_level();
                newRoleid = temp.getRole_id();
                promoted = true;
            }
        }
        GuildMemberDTO newRole = new GuildMemberDTO();
        newRole.setROLE_ID(newRoleid);
        newRole.setCHARACTER_ID(characterId);
        GuildMemberDTO save = memberRepo.save(newRole);
        }
        }
        return promoted;
    }
}

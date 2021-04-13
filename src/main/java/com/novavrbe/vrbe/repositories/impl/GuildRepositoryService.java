package com.novavrbe.vrbe.repositories.impl;

import com.novavrbe.vrbe.dto.*;
import com.novavrbe.vrbe.models.guildcontroller.GuildPermission;
import com.novavrbe.vrbe.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service per la gestione e l'accesso al database per le varie funzioni della gilda!
 */
@Service
public class GuildRepositoryService {

    @Autowired
    private GuildRepository guildRepository;

    @Autowired
    private GuildMemberListRepository guildMemberListRepository;

    @Autowired
    private  GuildMemeberRepository guildMemeberRepository;

    @Autowired
    private GuildRoleRepository guildRoleRepository;

    @Autowired
    private GuildBankRepository guildBankRepository;

    @Autowired
    private CharacterCvRepository characterCvRepository;

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
     * @return Lista dei membri V_GuildMembers
     */
    public List<V_GuildMembers> getGuildMembers(Integer guildId){
        List<V_GuildMembers> members = new ArrayList<>();
        if(guildId != null){
            members = guildMemberListRepository.findAllByGuildId(guildId);
        }
        return members;
    }

    /**
     * Torna il ruolo ricoperto dalla gilda di quel pg
     * @param characterId l'id del pg
     * @return le info sul ruolo o null
     */
    public V_GuildMembers getGuildMember(Integer characterId){
        V_GuildMembers tmp;
        Optional<V_GuildMembers> dto =guildMemberListRepository.findByCharacterId(characterId);
        tmp = dto.isEmpty()? null : dto.get();
        return tmp;
    }

    /**
     * Verifica che un character appartenga ad una qualunque gilda
     * @param charachaterId id del character
     * @return vero se non sei disoccupato, false altrimenti
     */
    public boolean checkEnrollment(Integer charachaterId) {
        boolean isPresente;
        Optional<GuildMemberDTO> member = guildMemeberRepository.findById(charachaterId);
        isPresente = member.isPresent();
        return isPresente;
    }

    /**
     * Torna le permission che hai per la gilda che stai vedendo.
     * @param charachaterId il characterId del pg
     * @param guildId la gilda che stai cercando di visualizzare
     * @return le permissiona associate al tuo pg.
     */
    public GuildPermission checkGuildPermission(Integer charachaterId, Integer guildId) {
        GuildPermission permission = new GuildPermission();
        Optional<V_GuildMembers> dto = guildMemberListRepository.findByCharacterId(charachaterId);
        if(dto.isPresent() && dto.get().getGuildId().equals(guildId)) {
         //Sei arruolato e sei nella gilda che stai visualizzando, capiamo se sei il capo :)
            permission.setPresente(true);
            List<GuildRoleDTO> listOfRoles = getRoleByGuildId(guildId);
            for (GuildRoleDTO role: listOfRoles) {
              if(role.getRoleId().equals(dto.get().getRoleId()))
                 permission.setManager(role.getIsManager());
            }
        }else {
           permission.setManager(false);
           permission.setPresente(false);
        }
    return permission;
    }

    public boolean addMember(Integer roleid, Integer characterId) {
        GuildMemberDTO newMember = new GuildMemberDTO();
        boolean saved = false;
        if(roleid != null && characterId != null){
            newMember.setCHARACTER_ID(characterId);
            newMember.setROLE_ID(roleid);
            GuildMemberDTO savedMember = guildMemeberRepository.save(newMember);
            updateCharacterCV(savedMember.getCHARACTER_ID(),savedMember.getROLE_ID(), new Date(new java.util.Date().getTime()));
            saved = true;
        }

        return saved;
    }

    public void deleteMember (Integer roleId, Integer characterId){
        GuildMemberDTO toDelete = new GuildMemberDTO();
        toDelete.setCHARACTER_ID(characterId);
        toDelete.setROLE_ID(roleId);
        guildMemeberRepository.delete(toDelete);
    }

    public boolean promoteMember(Integer characterId){
        boolean promoted = false;
        //intanto mi becco il ruolo ricoperto dal pg
        Optional<GuildMemberDTO> member = guildMemeberRepository.findById(characterId);
        Integer role_id;
        role_id = member.map(GuildMemberDTO::getROLE_ID).orElse(null);
        Optional<GuildRoleDTO> dto = (role_id != null) ? guildRoleRepository.findById(role_id) : Optional.empty();
        if(dto.isPresent()){
        List<GuildRoleDTO> listofpossibile = guildRoleRepository.findByGuildIdAndGuildLevelGreaterThan(dto.get().getGuildId(),dto.get().getGuildLevel());
        if(listofpossibile.size() > 0){
        Integer roleLevel = 2000;
        Integer newRoleid = 0;
        for (GuildRoleDTO temp: listofpossibile) {
            if(temp.getGuildLevel() < roleLevel){
                roleLevel = temp.getGuildLevel();
                newRoleid = temp.getRoleId();
                promoted = true;
            }
        }
        GuildMemberDTO newRole = new GuildMemberDTO();
        newRole.setROLE_ID(newRoleid);
        newRole.setCHARACTER_ID(characterId);
        GuildMemberDTO save = guildMemeberRepository.save(newRole);
        }
        }
        return promoted;
    }

    public boolean degradeMember(Integer characterId){
        boolean degradated = false;
        Optional<GuildMemberDTO> member = guildMemeberRepository.findById(characterId);
        Integer role_id;
        role_id = member.map(GuildMemberDTO::getROLE_ID).orElse(null);
        Optional<GuildRoleDTO> dto = (role_id != null) ? guildRoleRepository.findById(role_id) : Optional.empty();
        if(dto.isPresent()){
            List<GuildRoleDTO> listofpossibile = guildRoleRepository.findByGuildIdAndGuildLevelLessThan(dto.get().getGuildId(),dto.get().getGuildLevel());
            if(listofpossibile.size() > 0){
                Integer roleLevel = 1;
                Integer newRoleid = 9999;
                for (GuildRoleDTO temp: listofpossibile) {
                    if(temp.getGuildLevel() > roleLevel){
                        roleLevel = temp.getGuildLevel();
                        newRoleid = temp.getRoleId();
                        degradated = true;
                    }
                }
                GuildMemberDTO newRole = new GuildMemberDTO();
                newRole.setROLE_ID(newRoleid);
                newRole.setCHARACTER_ID(characterId);
                GuildMemberDTO save = guildMemeberRepository.save(newRole);

            }
        }
        return degradated;
    }


    /**
     * Torna il saldo del conto della gilda
     * @param guildId id della gilda
     * @return l'ammontare della gilda
     */
    public GuildBankDTO getGuildBank(Integer guildId){
        GuildBankDTO bankDTO = null;
        if(guildId != null){
            Optional<GuildBankDTO> dto = guildBankRepository.findById(guildId);
            bankDTO = dto.orElse(null);

        }
        return bankDTO;
    }

    /**
     * Metodo per prelevare dalla banca di gilda
     * @param guildId id della gilda
     * @param amount somma da ritirare
     * @return true se l'operazione è andata bene , false altrimenti
     */
    public boolean withdraw(Integer guildId, Integer amount){
        boolean save ;
        if (guildId != null){
            Optional<GuildBankDTO> dto = guildBankRepository.findById(guildId);
            if(dto.isPresent() && dto.get().getAmount() >= amount) {
                //devo avere i soldi nella banca di gilda
                dto.get().setAmount(dto.get().getAmount() - amount);
                save = true;
            }else {save = false;}
        }else  {save = false;}
        return save;
    }

    /**
     * Metodo per depositare nella banca di gilda
     * @param guildId id della gilda
     * @param amount somma da depositare
     * @return true se l'operazione è andata bene , false altrimenti
     */
    public boolean deposit(Integer guildId, Integer amount){
        boolean save ;
        if (guildId != null){
            Optional<GuildBankDTO> dto = guildBankRepository.findById(guildId);
            if(dto.isPresent() ) {
                //Posso sempre aggiungere soldi nella banca di gilda
                dto.get().setAmount(dto.get().getAmount() + amount);
                save = true;
            }else {save = false;}
        }else  {save = false;}
        return save;
    }

    public List<GuildRoleDTO> getRoleByGuildId(Integer guildId) {
        List<GuildRoleDTO> tmp = new ArrayList<>();
        if(guildId != null){
            tmp = guildRoleRepository.findByGuildId(guildId);
        }
        return tmp  ;
    }

    public List<CharacterCvDTO> getCharacterCv(Integer cId) {
        List<CharacterCvDTO> roles;
        roles = characterCvRepository.findAllByCharacterIdOrderByEnrollmentDateDesc(cId);
        return roles;
    }

    public GuildRoleDTO getRoleById(Integer roleId) {
        Optional<GuildRoleDTO> dto;
        dto = guildRoleRepository.findById(roleId);
        return dto.get();
    }

    public void updateCharacterCV(Integer character_id, Integer roleId, java.sql.Date date) {
        CharacterCvDTO nuovoRecord = new CharacterCvDTO();
        CharacterCvDTO actualCVRole = characterCvRepository.findAllByCharacterIdAndEnrollmentDateEquals(character_id,date);
        if(actualCVRole != null ){
            //vuol dire che oggi ho già un record nel cv, devo aggiornare
            actualCVRole.setRoleId(roleId);
            characterCvRepository.save(actualCVRole);

        }else{
            nuovoRecord.setCharacterId(character_id);
            nuovoRecord.setEnrollmentDate(date);
            nuovoRecord.setRoleId(roleId);
            characterCvRepository.save(nuovoRecord);
        }

    }
}

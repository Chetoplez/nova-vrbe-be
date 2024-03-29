package com.novavrbe.vrbe.repositories.impl;

import com.novavrbe.vrbe.dto.*;
import com.novavrbe.vrbe.models.guildcontroller.GetSalaryResponse;
import com.novavrbe.vrbe.models.guildcontroller.GuildPermission;
import com.novavrbe.vrbe.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    private GuildMemeberRepository guildMemeberRepository;

    @Autowired
    private GuildRoleRepository guildRoleRepository;

    @Autowired
    private GuildBankRepository guildBankRepository;

    @Autowired
    private CharacterCvRepository characterCvRepository;
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private DailySalaryRepository dailySalaryRepository;

    /**
     * Torna le informazioni della gilda dato il suo id
     *
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
     *
     * @param guildId L'id della gilda di cui vuoi i conoscere i membri
     * @return Lista dei membri V_GuildMembers
     */
    public List<V_GuildMembers> getGuildMembers(Integer guildId) {
        List<V_GuildMembers> members = new ArrayList<>();
        if (guildId != null) {
            members = guildMemberListRepository.findAllByGuildId(guildId);
        }
        return members;
    }

    /**
     * Torna il ruolo ricoperto dalla gilda di quel pg
     *
     * @param characterId l'id del pg
     * @return le info sul ruolo o null
     */
    public V_GuildMembers getGuildMember(Integer characterId) {
        V_GuildMembers tmp;
        Optional<V_GuildMembers> dto = guildMemberListRepository.findByCharacterId(characterId);
        tmp = !dto.isPresent() ? null : dto.get();
        return tmp;
    }

    /**
     * Verifica che un character appartenga ad una qualunque gilda
     *
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
     *
     * @param charachaterId il characterId del pg
     * @param guildId       la gilda che stai cercando di visualizzare
     * @return le permissiona associate al tuo pg.
     */
    public GuildPermission checkGuildPermission(Integer charachaterId, Integer guildId) {
        GuildPermission permission = new GuildPermission();
        Optional<V_GuildMembers> dto = guildMemberListRepository.findByCharacterId(charachaterId);
        if (dto.isPresent() && dto.get().getGuildId().equals(guildId)) {
            //Sei arruolato e sei nella gilda che stai visualizzando, capiamo se sei il capo :)
            permission.setPresente(true);
            List<GuildRoleDTO> listOfRoles = getRoleByGuildId(guildId);
            for (GuildRoleDTO role : listOfRoles) {
                if (role.getRoleId().equals(dto.get().getRoleId()))
                    permission.setManager(role.getIsManager());
            }
        } else {
            permission.setManager(false);
            permission.setPresente(false);
        }
        return permission;
    }

    public boolean addMember(Integer roleid, Integer characterId) {
        GuildMemberDTO newMember = new GuildMemberDTO();
        boolean saved = false;
        if (roleid != null && characterId != null) {
            newMember.setCHARACTER_ID(characterId);
            newMember.setROLE_ID(roleid);
            GuildMemberDTO savedMember = guildMemeberRepository.save(newMember);
            updateCharacterCV(savedMember.getCHARACTER_ID(), savedMember.getROLE_ID(), new Date(new java.util.Date().getTime()));
            saved = true;
        }

        return saved;
    }

    public void deleteMember(Integer roleId, Integer characterId) {
        GuildMemberDTO toDelete = new GuildMemberDTO();
        toDelete.setCHARACTER_ID(characterId);
        toDelete.setROLE_ID(roleId);
        guildMemeberRepository.delete(toDelete);
    }

    /**
     * Mi dice quale sarà il prossimo guildlevel di un charachter in caso di promozione
     * @param chId characterId
     * @return il prossimo livello di gilda , guildLevel Integer
     */
    public Integer getPossibleNextLevel(Integer chId){
        Integer nextLevel = 0;
        Optional<GuildMemberDTO> member = guildMemeberRepository.findById(chId);
        Integer role_id;
        role_id = member.map(GuildMemberDTO::getROLE_ID).orElse(null);
        Optional<GuildRoleDTO> dto = (role_id != null) ? guildRoleRepository.findById(role_id) : Optional.empty();
        if (dto.isPresent()) {
            List<GuildRoleDTO> listofpossibile = guildRoleRepository.findByGuildIdAndGuildLevelGreaterThan(dto.get().getGuildId(), dto.get().getGuildLevel());
            if (listofpossibile.size() > 0) {
                Integer roleLevel = 2000;
                Integer newRoleid = 0;
                for (GuildRoleDTO temp : listofpossibile) {
                    if (temp.getGuildLevel() < roleLevel) {
                        roleLevel =
                        nextLevel = temp.getGuildLevel();

                    }

                }
            }
        }
        return nextLevel;
    }

    public boolean promoteMember(Integer characterId ) {
        boolean promoted = false;
        //intanto mi becco il ruolo ricoperto dal pg
        Optional<GuildMemberDTO> member = guildMemeberRepository.findById(characterId);
        Integer role_id;
        role_id = member.map(GuildMemberDTO::getROLE_ID).orElse(null);
        Optional<GuildRoleDTO> dto = (role_id != null) ? guildRoleRepository.findById(role_id) : Optional.empty();
        if (dto.isPresent()) {
            List<GuildRoleDTO> listofpossibile = guildRoleRepository.findByGuildIdAndGuildLevelGreaterThan(dto.get().getGuildId(), dto.get().getGuildLevel());
            if (listofpossibile.size() > 0) {
                Integer roleLevel = 2000;
                Integer newRoleid = 0;
                for (GuildRoleDTO temp : listofpossibile) {
                    if (temp.getGuildLevel() < roleLevel) {
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

    public boolean degradeMember(Integer characterId) {
        boolean degradated = false;
        Optional<GuildMemberDTO> member = guildMemeberRepository.findById(characterId);
        Integer role_id;
        role_id = member.map(GuildMemberDTO::getROLE_ID).orElse(null);
        Optional<GuildRoleDTO> dto = (role_id != null) ? guildRoleRepository.findById(role_id) : Optional.empty();
        if (dto.isPresent()) {
            List<GuildRoleDTO> listofpossibile = guildRoleRepository.findByGuildIdAndGuildLevelLessThan(dto.get().getGuildId(), dto.get().getGuildLevel());
            if (listofpossibile.size() > 0) {
                Integer roleLevel = 1;
                Integer newRoleid = 9999;
                for (GuildRoleDTO temp : listofpossibile) {
                    if (temp.getGuildLevel() > roleLevel) {
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
     *
     * @param guildId id della gilda
     * @return l'ammontare della gilda
     */
    public GuildBankDTO getGuildBank(Integer guildId) {
        GuildBankDTO bankDTO = null;
        if (guildId != null) {
            Optional<GuildBankDTO> dto = guildBankRepository.findById(guildId);
            bankDTO = dto.orElse(null);

        }
        return bankDTO;
    }

    /**
     * Metodo per prelevare dalla banca di gilda
     *
     * @param guildId id della gilda
     * @param amount  somma da ritirare
     * @return true se l'operazione è andata bene , false altrimenti
     */
    public boolean withdraw(Integer guildId, Integer amount) {

        if (guildId != null) {
            Optional<GuildBankDTO> dto = guildBankRepository.findById(guildId);
            if (dto.isPresent() && dto.get().getAmount() >= amount) {
                dto.get().setAmount(dto.get().getAmount() - amount);
                guildBankRepository.save(dto.get());
                return true;
            }
        }
        return false;
    }

    /**
     * Metodo per depositare nella banca di gilda
     *
     * @param guildId id della gilda
     * @param amount  somma da depositare
     * @return true se l'operazione è andata bene , false altrimenti
     */
    public boolean deposit(Integer guildId, Integer amount) {
        if (guildId != null) {
            Optional<GuildBankDTO> dto = guildBankRepository.findById(guildId);
            if (dto.isPresent()) {
                //Posso sempre aggiungere soldi nella banca di gilda
                dto.get().setAmount(dto.get().getAmount() + amount);
                guildBankRepository.save(dto.get());
                return true;
            }
        }
        return false;
    }

    public List<GuildRoleDTO> getRoleByGuildId(Integer guildId) {
        List<GuildRoleDTO> tmp = new ArrayList<>();
        if (guildId != null) {
            tmp = guildRoleRepository.findByGuildId(guildId);
        }
        return tmp;
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

    /**
     * Inserisce un nuovo record nella tabella del characterCV, ma se il cambio di CV avviene lo stesso giorno, lo aggiorna per non avere troppo dentro.
     * @param character_id l'id del character che viene promosso, degratato
     * @param roleId il nuovo ruolo
     * @param date la data in cui viene inserito nel ruolo
     */
    public void updateCharacterCV(Integer character_id, Integer roleId, java.sql.Date date) {
        CharacterCvDTO nuovoRecord = new CharacterCvDTO();
        CharacterCvDTO actualCVRole = characterCvRepository.findAllByCharacterIdAndEnrollmentDateEquals(character_id, date);
        if (actualCVRole != null && !actualCVRole.getEnrollmentDate().equals(date)) {
            //vuol dire che oggi ho già un record nel cv, devo aggiornare
            actualCVRole.setRoleId(roleId);
            characterCvRepository.save(actualCVRole);

        } else {
            nuovoRecord.setCharacterId(character_id);
            nuovoRecord.setEnrollmentDate(date);
            nuovoRecord.setRoleId(roleId);
            characterCvRepository.save(nuovoRecord);
        }

    }


    public GetSalaryResponse getSalary(Integer chId) {
        Integer roleId;
        GetSalaryResponse retrieved = new GetSalaryResponse();
        retrieved.setRetrieved(false);
        retrieved.setMessage("stipendio già ritirato! Torna domani!");


        if (canGetSalary(chId)) {
            Optional<GuildMemberDTO> dto = guildMemeberRepository.findById(chId);
            if (dto.isPresent()) {
                roleId = dto.get().getROLE_ID();
                Optional<GuildRoleDTO> roleDTO = guildRoleRepository.findById(roleId);
                Optional<InventoryDto> inventoryDto = inventoryRepository.findById(chId);
                if (roleDTO.isPresent()) {
                    Optional<GuildBankDTO> bankDTO = guildBankRepository.findById(roleDTO.get().getGuildId());
                    GuildBankDTO bank = bankDTO.isPresent() ? bankDTO.get() : null;
                    if (bank != null && (bank.getAmount() >= roleDTO.get().getSalary())) {
                        InventoryDto saccoccia = inventoryDto.isPresent() ? inventoryDto.get() : null;
                        if (saccoccia != null) {
                            saccoccia.setGold(saccoccia.getGold() + roleDTO.get().getSalary());
                            inventoryRepository.save(saccoccia);
                            Optional<DailySalaryDto> salary = dailySalaryRepository.findByCharacterId(chId);
                            if (salary.isPresent()) {
                                DailySalaryDto tempSalary = salary.get();
                                tempSalary.setLastSalary(new Date(new java.util.Date().getTime()));
                                tempSalary.setBlockedUntil(new Date(new java.util.Date().getTime()));
                                dailySalaryRepository.save(tempSalary);
                            } else {
                                DailySalaryDto tempSalary = new DailySalaryDto();
                                tempSalary.setCharacterId(chId);
                                tempSalary.setLastSalary(new Date(new java.util.Date().getTime()));
                                tempSalary.setBlockedUntil(new Date(new java.util.Date().getTime()));
                                dailySalaryRepository.save(tempSalary);
                            }
                            bank.setAmount(bank.getAmount() - roleDTO.get().getSalary());
                            guildBankRepository.save(bank);
                            retrieved.setRetrieved(true);
                            retrieved.setMessage("Stipendio ritirato! Torna domani");
                        }
                    }else {
                        retrieved.setRetrieved(false);
                        retrieved.setMessage("La banca di Gilda non ha abbastanza denaro!");
                    }
                }
            }
        }
    return retrieved;
    }

    private boolean canGetSalary(Integer chId){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date(new java.util.Date().getTime());
        String testDate = sdf.format(today);

        java.util.Date oggi = null;
        try {
            oggi =  sdf.parse(testDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Optional<DailySalaryDto> salary = dailySalaryRepository.findByCharacterId(chId);
        if(!salary.isPresent())
            return true; //perché deve essere il primo stipendio
        else{
        Date lastSalary = salary.get().getLastSalary();
        return lastSalary.before(oggi);
        }
    }

    public Iterable<GuildDTO> getAllGuild() {
        return guildRepository.findAll();
    }
}

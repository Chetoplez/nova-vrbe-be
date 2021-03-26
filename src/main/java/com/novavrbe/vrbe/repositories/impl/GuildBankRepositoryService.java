package com.novavrbe.vrbe.repositories.impl;

import com.novavrbe.vrbe.dto.GuildBankDTO;
import com.novavrbe.vrbe.repositories.GuildBankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GuildBankRepositoryService {

    @Autowired
    private GuildBankRepository guildBankRepository;

    public GuildBankDTO getGuildBank(Integer guildId){
        GuildBankDTO bankDTO = null;
        if(guildId != null){
            Optional<GuildBankDTO> dto = guildBankRepository.findById(guildId);
            bankDTO = dto.isPresent() ? dto.get() : null;

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

}

package com.novavrbe.vrbe.repositories.impl;

import com.novavrbe.vrbe.dto.PresentiLuogoDto;
import com.novavrbe.vrbe.repositories.PresentiLuogoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class PresentiRepositoryService {

    @Autowired
    private PresentiLuogoRepository presentiLuogoRepository;


    /**
     * Aggiorna l'id luogo in cui si trova il characater che si sta spostando
     * @param idLuogo il nuovo luogo dove ci troviamo
     * @param characterId il pg che si spsota
     * @return true se mi sono spostato correttamente, false altrimenti
     */
    public boolean moveToluogo(Integer idLuogo, Integer characterId){
        boolean moved = false;
        Optional<PresentiLuogoDto> dto = presentiLuogoRepository.findById(characterId);
        if(dto.isPresent()){
            PresentiLuogoDto temp = dto.get();
            temp.setIdLuogo(idLuogo);
            presentiLuogoRepository.save(temp);
            moved = true;
        }

        return moved;
    }

    /**
     * Aggiorna il messaggio personale del pg, nello stato online
     * @param characterId l'id del personaggio
     * @param messaggio il nuovo messaggio da inserire
     * @return true se è stato cambiato correttamente, false altrimenti
     */
    public boolean changePersonalMessage(Integer characterId, String messaggio){
        boolean changed = false;
        Optional<PresentiLuogoDto> dto =  presentiLuogoRepository.findById(characterId);
        if(dto.isPresent()){
            PresentiLuogoDto tmp = dto.get();
            tmp.setMessaggio(messaggio);
            presentiLuogoRepository.save(tmp);
            changed = true;
        }
        return changed;
    }

    /**
     * Cambia la disponibilità online di un personaggio
     * @param characterId l'id del personaggio
     * @param available il nuovo stato online del pg
     * @return true se è stato cambiato correttamente, false altrimenti
     */
    public boolean changeAvailability(Integer characterId, boolean available){
        boolean changed = false;
        Optional<PresentiLuogoDto> dto =  presentiLuogoRepository.findById(characterId);
        if(dto.isPresent()){
            PresentiLuogoDto tmp = dto.get();
            tmp.setAvailable(available);
            presentiLuogoRepository.save(tmp);
            changed = true;
        }
        return changed;
    }
}

package com.novavrbe.vrbe.repositories.impl;

import com.novavrbe.vrbe.dto.CharacterStatisticsDto;
import com.novavrbe.vrbe.dto.CharacterTemporaryEffectDto;
import com.novavrbe.vrbe.dto.ChatMessageDto;
import com.novavrbe.vrbe.models.charactermodels.CharacterStatistic;
import com.novavrbe.vrbe.models.enumerations.Stat;
import com.novavrbe.vrbe.repositories.CharacterStatisticRepository;
import com.novavrbe.vrbe.repositories.CharacterTemporaryEffectsRepository;
import com.novavrbe.vrbe.repositories.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/***
 * Chat service per gestire i messaggi della chat.
 */
@Service
public class ChatRepositoryService {

    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private CharacterStatisticRepository statisticRepository;
    @Autowired
    private CharacterTemporaryEffectsRepository tempEffectRepository;

    /**
     *
     * @param newMessage il messaggio da inserire nella chat con i dettagli di chi manda
     * @return il messaggio appena inserito.
     */
    public ChatMessageDto addNewChatMessage(ChatMessageDto newMessage){
        return chatRepository.save(newMessage) ;
    }

    /**
     *
     * @param id l'id della chat per cui cerchiamo i messaggi
     * @param window l'intervallo temporale dal quale prendere i messaggi
     * @return la lista dei messaggi di quella chat partendo dal time.now - la windows
     */
    public List<ChatMessageDto> getChatMessages(Integer id, Long window) {
        return chatRepository.findByChatIdAndTimestampGreaterThan(id, window);
    }

    public boolean isUpdated(Integer chatId, Long lastUpdate){
        List<ChatMessageDto> newMessages = new ArrayList<>();
        newMessages = chatRepository.findByChatIdAndTimestampGreaterThan(chatId,lastUpdate);
        return newMessages.size() > 0;
    }

    /**
     * Torna il valore di una specifica stastistica di un PG, gia calcolato
     * @param cId il character di cui vogliamo la statistica
     * @param stat il nome della statistica di cui vogliamo il valore
     * @return oggetto contenenente il base stat e il suo modifier
     */
    public CharacterStatistic getStatValue(Integer cId, Stat stat) {
        //devo recuperare sia il valore base che il modificatore

        Optional<CharacterStatisticsDto> statDto =  statisticRepository.findById(cId);
        List<CharacterTemporaryEffectDto> tempEffectDto = tempEffectRepository.findTemporaryEffectForUser(cId);
        CharacterStatistic pgStat = getStatistic(stat, statDto.get(),tempEffectDto);

        return pgStat;
    }

    private CharacterStatistic getStatistic(Stat stat, CharacterStatisticsDto statDto, List<CharacterTemporaryEffectDto> tempEffectDto){
        CharacterStatistic temp = new CharacterStatistic();
        switch(stat){
            case DESTREZZA:
                temp.setBaseStat(statDto.getDestrezza());
                break;
            case COSTITUZIONE:
                temp.setBaseStat(statDto.getCostituzione());
                break;
            case FORZA:
                temp.setBaseStat(statDto.getForza());
                break;
            case INTELLIGENZA:
                temp.setBaseStat(statDto.getIntelligenza());
                break;
            case SAGGEZZA:
                temp.setBaseStat(statDto.getSaggezza());
                break;
            default:
                break;
        }
        if(!tempEffectDto.isEmpty()){
            for (CharacterTemporaryEffectDto dto: tempEffectDto ) {
                if(dto.getStat().equalsIgnoreCase(stat.name())){
                    temp.setModified(BigDecimal.valueOf(dto.getModifier()));
                    break;
                }
            }
        }else {temp.setModified(BigDecimal.ZERO);}

        temp.setStatName(stat);
        return temp;
    }
}

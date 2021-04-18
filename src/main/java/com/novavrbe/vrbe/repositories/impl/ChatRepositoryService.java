package com.novavrbe.vrbe.repositories.impl;

import com.novavrbe.vrbe.dto.CharacterDto;
import com.novavrbe.vrbe.dto.ChatMessageDto;
import com.novavrbe.vrbe.dto.DailyExpDto;
import com.novavrbe.vrbe.repositories.CharacterRepository;
import com.novavrbe.vrbe.repositories.ChatRepository;
import com.novavrbe.vrbe.repositories.DailyExpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
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
    private DailyExpRepository dailyExpRepository;

    @Autowired
    private CharacterRepository characterRepository;

    @Autowired
    private Environment env;



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

    public Boolean couldUpdateDailyExp(Integer characterId) {
     Optional<DailyExpDto>  dailyDto = dailyExpRepository.findByCharacterIdAndExpDateEquals(characterId,new Date(new java.util.Date().getTime()));
     if(dailyDto.isPresent())
     {
        return dailyDto.get().getDailyExp()< Integer.parseInt(env.getProperty("exp.total.daily")) ? true : false;
     } else return true;
    }

    public void addDailyExp(Integer characterId, Integer exp) {
        Optional<DailyExpDto>  dailyDto = dailyExpRepository.findByCharacterIdAndExpDateEquals(characterId,new Date(new java.util.Date().getTime()));
        boolean canUpdate = false;
        Integer numExpToAdd = 0;
        //in caso lo risolve Ciccio
        if(dailyDto.isPresent()){
            Integer beforeUpdate = dailyDto.get().getDailyExp();
            Integer numExp = ((dailyDto.get().getDailyExp() + exp) >  Integer.parseInt(env.getProperty("exp.total.daily")) )? Integer.parseInt(env.getProperty("exp.total.daily")) : dailyDto.get().getDailyExp() + exp;
            dailyDto.get().setDailyExp(numExp);
            dailyExpRepository.save(dailyDto.get());
            numExpToAdd = dailyDto.get().getDailyExp() - beforeUpdate;
            canUpdate = true;
        }else
        {
            DailyExpDto dailyDtoTmp = new DailyExpDto();
            dailyDtoTmp.setCharacterId(characterId);
            dailyDtoTmp.setDailyExp(exp);
            dailyDtoTmp.setExpDate(new Date(new java.util.Date().getTime()));
            dailyExpRepository.save(dailyDtoTmp);
            numExpToAdd = exp;
            canUpdate = true;
        }

        Optional<CharacterDto> characerDto = characterRepository.findById(characterId);
        if(characerDto.isPresent() && canUpdate){
            CharacterDto pg = characerDto.get();
            BigDecimal actualExp = pg.getExperience();
            BigDecimal actualTotalExp = pg.getTotalExperience();
            pg.setExperience(actualExp.add(BigDecimal.valueOf(numExpToAdd)));
            pg.setTotalExperience(actualTotalExp.add(BigDecimal.valueOf(exp)));
            characterRepository.save(pg);
        }

    }
}

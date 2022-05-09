package com.novavrbe.vrbe.repositories.impl;

import com.novavrbe.vrbe.dto.*;
import com.novavrbe.vrbe.models.charactermodels.CharacterStatistic;
import com.novavrbe.vrbe.models.enumerations.Stat;
import com.novavrbe.vrbe.repositories.*;
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
    private ChatMessageRepository chatMessageRepository;
    @Autowired
    private CharacterStatisticRepository statisticRepository;
    @Autowired
    private CharacterTemporaryEffectsRepository tempEffectRepository;

    @Autowired
    private DailyExpRepository dailyExpRepository;

    @Autowired
    private CharacterRepository characterRepository;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private Environment env;



    /**
     *
     * @param newMessage il messaggio da inserire nella chat con i dettagli di chi manda
     * @return il messaggio appena inserito.
     */
    public ChatMessageDto addNewChatMessage(ChatMessageDto newMessage){
        return chatMessageRepository.save(newMessage) ;
    }

    /**
     *
     * @param id l'id della chat per cui cerchiamo i messaggi
     * @param window l'intervallo temporale dal quale prendere i messaggi
     * @return la lista dei messaggi di quella chat partendo dal time.now - la windows
     */
    public List<ChatMessageDto> getChatMessages(Integer id, Long window) {
        return chatMessageRepository.findByChatIdAndTimestampGreaterThan(id, window);
    }

    public boolean isUpdated(Integer chatId, Long lastUpdate){
        List<ChatMessageDto> newMessages = new ArrayList<>();
        newMessages = chatMessageRepository.findByChatIdAndTimestampGreaterThan(chatId,lastUpdate);
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

    public ArrayList<ChatDto> getChatList() {
        ArrayList<ChatDto> temp = (ArrayList<ChatDto>) chatRepository.findAll();
        return temp;
    }
}

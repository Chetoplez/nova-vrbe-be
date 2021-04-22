package com.novavrbe.vrbe.business;

import com.novavrbe.vrbe.dto.ChatMessageDto;
import com.novavrbe.vrbe.models.charactermodels.CharacterStatistic;
import com.novavrbe.vrbe.models.chatcontroller.*;
import com.novavrbe.vrbe.models.enumerations.ChatAction;
import com.novavrbe.vrbe.models.enumerations.Stat;
import com.novavrbe.vrbe.repositories.impl.ChatRepositoryService;
import com.novavrbe.vrbe.utils.ChatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class ChatBusiness {

    @Autowired
    private ChatRepositoryService chatRepositoryService;

    @Autowired
    private Environment env;

    /**
     * Questo metodo permette di inserire un messaggio in chat.
     * @param request Oggetto contenente i parametri per inserire il messaggio. Id della chat attuale, messaggio da insierire
     * @return TRUE se inserito correttamente ; FALSE altrimenti
     */
    public ResponseEntity<AddMessageResponse> addMessage(AddMessageRequest request){
        ResponseEntity<AddMessageResponse> response ;
        ChatMessage newMessage = request.getChatMessage();
        String chatId = request.getChatId();

        if(!StringUtils.hasText(request.getChatId()) || request.getChatMessage() == null){
            response = new ResponseEntity<>(new AddMessageResponse(), HttpStatus.BAD_REQUEST);
            return response;
        }
        //Devo trasformarare la request nell'oggetto da infilare nel database
        ChatMessageDto messageDto = ChatUtils.fillMessageDto(newMessage,chatId);

        if(newMessage.getAction().equalsIgnoreCase(ChatAction.PARLA.name()) && couldUpdateDailyExp(Integer.parseInt(newMessage.getCharacterId()))){
            //Se siamo qui aggiungiamo gli xp
            Integer valueExp = getExperienceFromActionMessage(newMessage.getTesto());
            addDailyExp(Integer.parseInt(newMessage.getCharacterId()),valueExp);
        }

        //Qui uso il mio service per inserire a Databse il messaggio :)
        ChatMessageDto dbDto =  chatRepositoryService.addNewChatMessage(messageDto);
        AddMessageResponse messageResponse = new AddMessageResponse();
        messageResponse.setChatRetrieved(true);
        response = new ResponseEntity<>(messageResponse,HttpStatus.OK);

        return response;
    }


    private Integer getExperienceFromActionMessage(String text){

       return ( (Integer.parseInt(env.getProperty("chat.message.action.length.minvalue")) < text.length()) || (text.length() > Integer.parseInt(env.getProperty("chat.message.action.length.maxvalue"))) ) ? Integer.parseInt( env.getProperty("chat.message.exp.maxvalue")) : Integer.parseInt(env.getProperty("chat.message.exp.minvalue"));

    }

    /**
     * Mi dice se il pg ha raggiunto o meno la soglia massima giornaliera di EXP
     * @param characterId l'id del pg
     * @return true se ancora può accumulare exp o false altrimenti
     */
    private Boolean couldUpdateDailyExp(Integer characterId){
        return chatRepositoryService.couldUpdateDailyExp(characterId);
    }

    /**
     * Metodo che permette di cancellare un messaggio della chat.
     * @param request DeleteMessageRequest che ha dentro i parametri per canchellare
     * @return TRUE se l'operazione è andata a buon fine; FALSE altrimenti
     */
    public ResponseEntity<DeleteMessageResponse> deleteMessage(DeleteMessageRequest request){
        ResponseEntity<DeleteMessageResponse> response = null;


        if(!StringUtils.hasText(request.getChatId()) || request.getMessageId() == null){
            response = new ResponseEntity<>(new DeleteMessageResponse(), HttpStatus.BAD_REQUEST);
            return response;
        }
        return response;
    }

    /**
     * Questo metodo ritora la lista dei messaggi presenti nella chat in cui siamo attualmente. I messaggi non vengono tornati tutti, ma solo quelli dall'ora
     * attuale ed indietro per window millisecondi.
     * @param id il chatId
     * @param window finestra temporale da cui prendere i messaggi, occhio. è in millisecondi!
     * @return La lista dei messaggi della chat da time.now() - window
     */
    public ResponseEntity<GetChatResponse> getChatById(String id, String window) {
        ResponseEntity<GetChatResponse> response ;
        List<ChatMessageDto> chatList ;
        List<ChatMessage> chatMessageList ;

        if(!StringUtils.hasText(id) && !StringUtils.hasText(window)){
            response = new ResponseEntity<>(new GetChatResponse(), HttpStatus.BAD_REQUEST);
            return response;
        }

         chatList =  chatRepositoryService.getChatMessages(Integer.parseInt(id), Long.parseLong(window));
         chatMessageList = ChatUtils.fillChatMessagefromDto(chatList);
         GetChatResponse res = new GetChatResponse();
         res.setChatMessageList(chatMessageList);
         response = new ResponseEntity<>(res, HttpStatus.OK);

        return response;
    }

    /**
     * Mi dice, per quella chatId se c'è un messaggio più recente rispetto all'ultimo aggiornamento che hai, in quel caso torna true
     * @param id l'id della chat
     * @param tms il timestamp del tuo ultimo aggiornamento
     * @return true se a database, per quell'ID c'è un timestamp maggiore, false altrimenti
     */
    public ResponseEntity<IsChatUpdatedResponse> isChatUpdated(String id, String tms) {
        ResponseEntity<IsChatUpdatedResponse> response;
        if(!StringUtils.hasText(id) || !StringUtils.hasText((tms))){
            response = new ResponseEntity<>(new IsChatUpdatedResponse(),HttpStatus.BAD_REQUEST);
            return response;
        }
        Integer chatId = Integer.parseInt(id);
        Long lastUpdate = Long.parseLong(tms);
        IsChatUpdatedResponse res = new IsChatUpdatedResponse();
        res.setUpdated(chatRepositoryService.isUpdated(chatId,lastUpdate));
        response = new ResponseEntity<>(res,HttpStatus.OK);
        return response;
    }

    /**
     * Aggiorn l'esperienza del pg e allinea la tabella dell'esperienza giornaliera.
     * @param characterId l'id del pg da aggiornare
     * @param exp il valore il base alla lunghezza dell'azione scritta
     *
     */
    private void addDailyExp(Integer characterId, Integer exp){
        chatRepositoryService.addDailyExp(characterId, exp);
    }

    /**
     * Genera un lancio casuale dei dadi basandosi sulle statische del giocatore
     * @param request request per generare il valore casuali di dado
     * @return torna true se è andato a buone fine, false altrimenti
     */
    public ResponseEntity<AddMessageResponse> rollDice(RollDiceRequest request) {
        ResponseEntity<AddMessageResponse> response = null;
        if(!StringUtils.hasText(request.getCharachterId()) || !StringUtils.hasText(request.getStatName()) || !StringUtils.hasText(request.getChatId())){
            response = new ResponseEntity<>(new AddMessageResponse(), HttpStatus.BAD_REQUEST);
            return response;
        }
        Integer chatId = Integer.parseInt(request.getChatId());
        Integer cId = Integer.parseInt(request.getCharachterId());
        String stat = request.getStatName();

        //Mi prendo il valore della stat inclusa del modificatore (che può essere zero)
        CharacterStatistic statValue = chatRepositoryService.getStatValue(cId, Stat.valueOf(stat));
        //Da questo valore , genero un numero casuale da 1 al massimo valore della stat.
        int maxStatValue = statValue.getBaseStat() + statValue.getModified().intValue();
        int randomNum = ThreadLocalRandom.current().nextInt(1, maxStatValue + 1);
        //Bene, abbiamo generato il valore random del dado basato sulla statistica (eventualmente modificata), ora inseriamo un messaggio
        String testo = "Lancia un Dado su " + stat +": "+"Il risultato è "+randomNum+" su "+maxStatValue;

        ChatMessageDto diceMessage = new ChatMessageDto();
        diceMessage.setChatId(chatId);
        diceMessage.setAction(ChatAction.DADI.name());
        diceMessage.setCarica(request.getCarica());
        diceMessage.setSender(request.getSender());
        diceMessage.setImg(request.getImg());
        diceMessage.setTag(request.getTag());
        diceMessage.setTimestamp(new Date().getTime());
        diceMessage.setTooltip_carica(diceMessage.getCarica());
        diceMessage.setTesto(testo);
        chatRepositoryService.addNewChatMessage(diceMessage);
        AddMessageResponse res = new AddMessageResponse();
        res.setChatRetrieved(true);
        response = new ResponseEntity<>(res, HttpStatus.OK);
        return response;
    }

    /**
     * Questa funzione effettua un confronto tra le caratteristiche di Destrezza di attaccante e difensore.
     * @param request la richiesta di attaacco da un pg nei cronfronti di un altro
     *
     * @return se La Destrezza dell'attaccante è Maggiore del Difensore: true, altrimenti false.
     */
    public ResponseEntity<AttackResponse> attack(AttackRequest request) {
        ResponseEntity<AttackResponse> response;
        if(!StringUtils.hasText(request.getAttackerId()) || !StringUtils.hasText(request.getDefenderId()) || !StringUtils.hasText(request.getChatId())){
            response = new ResponseEntity<>(new AttackResponse(),HttpStatus.BAD_REQUEST);
            return response;
        }

        Integer attackerId = Integer.parseInt(request.getAttackerId());
        Integer defenderId = Integer.parseInt(request.getDefenderId()); //TODO aggiungere una funzione per verificare che il difensore sia dentro la chat di gioco
        Integer chatId = Integer.parseInt(request.getChatId());

        AttackResult result = attackCharacter(attackerId,defenderId);
        //Ora inserisco un messaggio in chat e poi torno il risultato dell'azione effettuata:
        ChatMessageDto attackMessage = new ChatMessageDto();
        attackMessage.setChatId(chatId);
        attackMessage.setAction(ChatAction.ATTACCA.name());
        attackMessage.setCarica(request.getCarica());
        attackMessage.setCharacterId(attackerId.toString());
        attackMessage.setSender(request.getSender());
        attackMessage.setImg(request.getImg());
        attackMessage.setTag(request.getTag());
        attackMessage.setTimestamp(new Date().getTime());
        attackMessage.setTooltip_carica(request.getCarica());
        String esito = result.isColpito() ? "COLPITO" : "MANCATO";
        String testo = "Tenta di attaccare " +request.getDefernderName()+": "+esito+" : ("+request.getSender()+" Ha totalizzato: "
                +result.getAttackResult()+" e "+request.getDefernderName()+" ha totalizzato: "+result.getDefenseResult()+")";
        attackMessage.setTesto(testo);
        chatRepositoryService.addNewChatMessage(attackMessage);
        AttackResponse attackResponse = new AttackResponse();
        attackResponse.setAttacked(result.isColpito());
        response = new ResponseEntity<>(attackResponse, HttpStatus.OK);

        return response;
    }


    /**
     * Metodo accessorio per generare i valori di destrezza dei due contendenti, torna il risultato dello scontro
     * @param attacker characterId dell'attaccante
     * @param defender characterId del difensore
     * @return Oggetto contenente i risultati ottenuti e se è stato colpito o meno.
     */
    private AttackResult attackCharacter(Integer attacker, Integer defender){
        AttackResult res = new AttackResult();
        CharacterStatistic attackerStat = chatRepositoryService.getStatValue(attacker, Stat.valueOf("DESTREZZA"));
        CharacterStatistic defenderStat = chatRepositoryService.getStatValue(defender, Stat.valueOf("DESTREZZA"));
        //bene, ho le statistiche di destrezza di chi attacca e difende. Genero casualmente due valori
        int attackerValue = ThreadLocalRandom.current().nextInt(1, (attackerStat.getBaseStat()+attackerStat.getModified().intValue()) + 1);
        int defenderValue = ThreadLocalRandom.current().nextInt(1, (defenderStat.getBaseStat()+defenderStat.getModified().intValue()) + 1);
        boolean hit = (attackerValue > defenderValue);
        res.setColpito(hit);
        res.setAttackResult(attackerValue);
        res.setDefenseResult(defenderValue);
        return res;

    }

    /**
     * Questa funziona calcola solo il danno che un giocatore fa a seguito di un attacco.
     * @param request la richiesta contenente tutti i parametri necessari per poter attaccare
     * @return torna true se l'azione è andata a buon fine, false altrimenti e scrive un messaggio in chat
     */
    public ResponseEntity<AttackResponse> hitCharacter(AttackRequest request){

        return null;
    }
}

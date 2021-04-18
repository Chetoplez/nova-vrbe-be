package com.novavrbe.vrbe.business;

import com.novavrbe.vrbe.dto.ChatMessageDto;
import com.novavrbe.vrbe.models.chatcontroller.*;
import com.novavrbe.vrbe.models.enumerations.ChatAction;
import com.novavrbe.vrbe.repositories.impl.ChatRepositoryService;
import com.novavrbe.vrbe.utils.ChatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

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
        Integer valueExp = 0;
        if(!StringUtils.hasText(request.getChatId()) || request.getChatMessage() == null){
            response = new ResponseEntity<>(new AddMessageResponse(), HttpStatus.BAD_REQUEST);
            return response;
        }
        //Devo trasformarare la request nell'oggetto da infilare nel database
        ChatMessageDto messageDto = ChatUtils.fillMessageDto(newMessage,chatId);

        if(newMessage.getAction().equalsIgnoreCase(ChatAction.PARLA.name()) && couldUpdateDailyExp(Integer.parseInt(newMessage.getCharacterId()))){
            //Se siamo qui aggiungiamo gli xp
            valueExp = getExperienceFromActionMessage(newMessage.getTesto());
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
}

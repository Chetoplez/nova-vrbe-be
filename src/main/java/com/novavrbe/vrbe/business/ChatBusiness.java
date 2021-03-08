package com.novavrbe.vrbe.business;

import com.novavrbe.vrbe.dto.ChatMessageDto;
import com.novavrbe.vrbe.models.chatcontroller.*;
import com.novavrbe.vrbe.repositories.impl.ChatRepositoryService;
import com.novavrbe.vrbe.utils.ChatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class ChatBusiness {

    @Autowired
    private ChatRepositoryService chatRepositoryService;

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

        //Qui uso il mio service per inserire a Databse il messaggio :)
        ChatMessageDto dbDto =  chatRepositoryService.addNewChatMessage(messageDto);
        AddMessageResponse messageResponse = new AddMessageResponse();
        messageResponse.setChatRetrieved(dbDto != null);
        response = new ResponseEntity<>(messageResponse,HttpStatus.OK);

        return response;
    }

    public ResponseEntity<DeleteMessageResponse> deleteMessage(DeleteMessageRequest request){
        ResponseEntity<DeleteMessageResponse> response = null;


        if(!StringUtils.hasText(request.getChatId()) || request.getMessageId() == null){
            response = new ResponseEntity<>(new DeleteMessageResponse(), HttpStatus.BAD_REQUEST);
            return response;
        }
        return response;
    }

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
}

package com.novavrbe.vrbe.repositories.impl;

import com.novavrbe.vrbe.dto.ChatMessageDto;
import com.novavrbe.vrbe.repositories.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.List;

/***
 * Chat service per gestire i messaggi della chat.
 */
@Service
public class ChatRepositoryService {

    @Autowired
    private ChatRepository chatRepository;

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
        return chatRepository.findMessageByChatId(id, window);

    }
}

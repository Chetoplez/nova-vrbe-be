package com.novavrbe.vrbe.utils;

import com.novavrbe.vrbe.dto.CharacterDto;
import com.novavrbe.vrbe.dto.ChatMessageDto;
import com.novavrbe.vrbe.dto.V_GuildMembers;
import com.novavrbe.vrbe.models.chatcontroller.ChatMessage;
import com.sun.istack.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ChatUtils {

    public static ChatMessageDto fillMessageDto(@NotNull ChatMessage message, String chatId, CharacterDto sender, V_GuildMembers lavoro){
        ChatMessageDto dto = new ChatMessageDto();

        dto.setAction(message.getAction());
        if(lavoro != null){
            dto.setCarica(lavoro.getRoleImg() );
            dto.setTooltip_carica(lavoro.getRoleName());
        }

        dto.setSender(sender.getCharacterName()+' '+sender.getCharacterSurname());
        dto.setCharacterId(sender.getCharacterId().toString());
        dto.setReceiver(message.getReceiver());

        dto.setChatId(Integer.parseInt(chatId));
        dto.setImg(sender.getCharacterImg());
        dto.setTimestamp(message.getTimestamp());
        dto.setTag(message.getTag());
        dto.setTesto(message.getTesto());

        return dto;
    }

    public static List<ChatMessage> fillChatMessagefromDto(@NotNull List<ChatMessageDto> dbDtoList){
         List<ChatMessage> _temp = new ArrayList<>();
        for (ChatMessageDto dtoMessage: dbDtoList) {
            ChatMessage tmp = new ChatMessage();

            tmp.setId(dtoMessage.getId().toString());
            tmp.setAction(dtoMessage.getAction());
            tmp.setCarica(dtoMessage.getCarica());
            tmp.setImg(dtoMessage.getImg());
            tmp.setReceiver(dtoMessage.getReceiver());
            tmp.setSender(dtoMessage.getSender());
            tmp.setCharacterId(dtoMessage.getCharacterId());
            tmp.setTag(dtoMessage.getTag());
            tmp.setTesto(dtoMessage.getTesto());
            tmp.setTimestamp(dtoMessage.getTimestamp());
            tmp.setTooltip_carica(dtoMessage.getTooltip_carica());

            _temp.add(tmp);

        }

         return _temp;
    }

    /*Qui dentro ci va il metodo per contare quanti exp assegnare??*/
}

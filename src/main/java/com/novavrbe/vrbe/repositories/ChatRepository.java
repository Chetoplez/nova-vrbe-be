package com.novavrbe.vrbe.repositories;

import com.novavrbe.vrbe.dto.ChatMessageDto;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface ChatRepository extends CrudRepository<ChatMessageDto, Integer>, JpaSpecificationExecutor<ChatMessageDto> {
    default List<ChatMessageDto> findMessageByChatId(Integer id, Long window){
        final Date now = new Date();
        return findAll(
                (root, query, cb) -> cb.greaterThan(root.get("timestamp"), now.getTime() - window).in(cb.equal(root.get("chatId"), id))
        );
    }

}

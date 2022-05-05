package com.novavrbe.vrbe.repositories;

import com.novavrbe.vrbe.dto.ChatMessageDto;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface ChatMessageRepository extends CrudRepository<ChatMessageDto, Integer>, JpaSpecificationExecutor<ChatMessageDto> {
    default List<ChatMessageDto> findMessageByChatId(Integer id, Long window){
        final Date now = new Date();

        Specification<ChatMessageDto> chatidspec = (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("chatId"), id);

        Specification<ChatMessageDto> messageFrom = (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("timestamp"), now.getTime() - window);
        return findAll(Specification.where(chatidspec).and(messageFrom));
    }

    List<ChatMessageDto> findByChatIdAndTimestampGreaterThan(Integer chatId, Long lastUpdate);



}

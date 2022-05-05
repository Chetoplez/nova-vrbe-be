package com.novavrbe.vrbe.repositories;

import com.novavrbe.vrbe.dto.ChatDto;
import org.springframework.data.repository.CrudRepository;

public interface ChatRepository extends CrudRepository<ChatDto, Integer> {
}

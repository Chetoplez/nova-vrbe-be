package com.novavrbe.vrbe.repositories;

import com.novavrbe.vrbe.dto.GuildMemberDTO;
import org.springframework.data.repository.CrudRepository;

public interface GuildMemeberRepository extends CrudRepository<GuildMemberDTO, Integer> {
}

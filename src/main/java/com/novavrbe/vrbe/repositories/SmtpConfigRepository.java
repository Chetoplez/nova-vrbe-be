package com.novavrbe.vrbe.repositories;

import com.novavrbe.vrbe.dto.SmtpConfigDto;
import org.springframework.data.repository.CrudRepository;

public interface SmtpConfigRepository extends CrudRepository<SmtpConfigDto, Integer> {
}

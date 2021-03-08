package com.novavrbe.vrbe.repositories;

import com.novavrbe.vrbe.dto.InventoryDto;
import org.springframework.data.repository.CrudRepository;

public interface InventoryRepository extends CrudRepository<InventoryDto, Integer> {
}

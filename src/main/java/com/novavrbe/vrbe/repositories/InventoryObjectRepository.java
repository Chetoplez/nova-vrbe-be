package com.novavrbe.vrbe.repositories;

import com.novavrbe.vrbe.dto.InventoryObjectDto;
import org.springframework.data.repository.CrudRepository;

public interface InventoryObjectRepository extends CrudRepository<InventoryObjectDto, Integer> {
}

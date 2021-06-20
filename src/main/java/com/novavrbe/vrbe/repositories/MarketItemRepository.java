package com.novavrbe.vrbe.repositories;

import com.novavrbe.vrbe.dto.MarketItemDTO;
import org.springframework.data.repository.CrudRepository;

public interface MarketItemRepository extends CrudRepository<MarketItemDTO,Integer> {
}

package com.novavrbe.vrbe.repositories.impl;

import com.novavrbe.vrbe.dto.MarketItemDTO;
import com.novavrbe.vrbe.models.marketcontroller.MarketItem;
import com.novavrbe.vrbe.repositories.MarketItemRepository;
import com.novavrbe.vrbe.utils.MarketUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MarketRepositoryService {

    @Autowired
    private MarketItemRepository marketItemRepository;

    public MarketItem getMarketItemById(Integer id){
        MarketItem item = null;
        Optional<MarketItemDTO> itemDTO = marketItemRepository.findById(id);
        item = (itemDTO != null && itemDTO.get() != null) ? MarketUtils.getMarketItemFromDTO(itemDTO.get()) : null;

        return item;
    }

}

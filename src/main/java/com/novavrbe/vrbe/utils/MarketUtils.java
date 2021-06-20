package com.novavrbe.vrbe.utils;

import com.novavrbe.vrbe.dto.MarketItemDTO;
import com.novavrbe.vrbe.models.marketcontroller.MarketItem;
import com.sun.istack.NotNull;

public class MarketUtils {

    public static MarketItem getMarketItemFromDTO(@NotNull MarketItemDTO itemDTO){
        MarketItem item = new MarketItem();

        item.setItemId(itemDTO.getMarketItemId());
        item.setDescription(itemDTO.getDescription());
        item.setName(itemDTO.getName());
        item.setIllimited(itemDTO.isIllimited());
        item.setPrice(itemDTO.getPrice());
        item.setQuantity(itemDTO.getQuantity());


        return item;
    }
}

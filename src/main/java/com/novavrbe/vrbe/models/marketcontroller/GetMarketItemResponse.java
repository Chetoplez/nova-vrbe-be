package com.novavrbe.vrbe.models.marketcontroller;

import lombok.Data;

import java.util.ArrayList;

@Data
public class GetMarketItemResponse {
    private ArrayList<MarketItem> items;
}

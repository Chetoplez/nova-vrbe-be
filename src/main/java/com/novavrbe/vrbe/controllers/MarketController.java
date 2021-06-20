package com.novavrbe.vrbe.controllers;

import com.novavrbe.vrbe.business.MarketBusiness;
import com.novavrbe.vrbe.models.marketcontroller.GetMarketItemRequest;
import com.novavrbe.vrbe.models.marketcontroller.GetMarketItemResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/market")
public class MarketController {

    @Autowired
    private MarketBusiness business;

    @PostMapping("/findItem")
    public ResponseEntity<GetMarketItemResponse> findItem(@RequestBody GetMarketItemRequest request){
        return business.getItem(request);
    }

}

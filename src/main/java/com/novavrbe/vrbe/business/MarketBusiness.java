package com.novavrbe.vrbe.business;

import com.novavrbe.vrbe.models.marketcontroller.GetMarketItemRequest;
import com.novavrbe.vrbe.models.marketcontroller.GetMarketItemResponse;
import com.novavrbe.vrbe.repositories.impl.MarketRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class MarketBusiness {

    @Autowired
    private MarketRepositoryService repositoryService;

    public ResponseEntity<GetMarketItemResponse> getItem(GetMarketItemRequest request) {
        ResponseEntity<GetMarketItemResponse> response = null;

        if(!StringUtils.hasText(request.getName()) && request.getId() == null){
            response = new ResponseEntity<>(new GetMarketItemResponse(), HttpStatus.BAD_REQUEST);
            return response;
        }

        GetMarketItemResponse getMarketItemResponse = new GetMarketItemResponse();

        if(request.getId() != null){

        }else{
            //TODO
        }

        response = new ResponseEntity<GetMarketItemResponse>(getMarketItemResponse, HttpStatus.OK);

        return response;
    }
}

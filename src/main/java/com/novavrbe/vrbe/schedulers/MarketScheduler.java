package com.novavrbe.vrbe.schedulers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MarketScheduler {

    private static final Logger log = LoggerFactory.getLogger(MarketScheduler.class);

    @Scheduled(fixedRate = 5000)
    public void checkForExpiredItems() {

    }
}

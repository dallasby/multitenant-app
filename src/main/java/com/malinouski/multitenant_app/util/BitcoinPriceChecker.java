package com.malinouski.multitenant_app.util;

import com.malinouski.multitenant_app.service.destination_service.BitcoinPriceDestinationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BitcoinPriceChecker {
    private final BitcoinPriceDestinationService bitcoinPriceDestinationService;

    public void checkBtcPrice() {
        double btcPrice = bitcoinPriceDestinationService.getBitcoinPrice();
        System.out.println("Current BTC price: $" + btcPrice);
    }
}

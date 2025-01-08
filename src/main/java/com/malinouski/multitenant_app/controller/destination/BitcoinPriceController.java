package com.malinouski.multitenant_app.controller.destination;

import com.malinouski.multitenant_app.service.destination_service.BitcoinPriceDestinationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bitcoin")
public class BitcoinPriceController {
    private final BitcoinPriceDestinationService bitcoinPriceDestinationService;

    @GetMapping("/price")
    public double getBitcoinPrice() {
        return bitcoinPriceDestinationService.getBitcoinPrice();
    }
}

package com.malinouski.multitenant_app.service.destination_service.impl;

import com.malinouski.multitenant_app.dto.BitcoinPriceResponse;
import com.malinouski.multitenant_app.service.destination_service.BitcoinPriceDestinationService;
import com.sap.cloud.sdk.cloudplatform.connectivity.DestinationAccessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class BitcoinPriceDestinationServiceImpl implements BitcoinPriceDestinationService {
    @Value("${BTP_ENV}")
    private String environment;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public double getBitcoinPrice() {
        log.info("Getting bitcoin price from environment {}", environment);
        if ("BTP".equals(environment)) {
            log.info("Getting bitcoin price from SAP environment");
            return fetchPriceFromSAPDestination();
        } else {
            log.info("Getting bitcoin price from mocked price");
            return getMockBitcoinPrice();
        }
    }

    private double getMockBitcoinPrice() {
        return 100_000.00;
    }

    private double fetchPriceFromSAPDestination() {
        try {
            HttpDestination destination = DestinationAccessor.getDestination("BtcPriceAPI").asHttp();
            String apiUrl = destination.getUri().toString();
            BitcoinPriceResponse response = restTemplate.getForObject(apiUrl, BitcoinPriceResponse.class);

            if (response != null && response.bitcoin() != null) {
                return response.bitcoin().usd();
            } else {
                throw new RuntimeException("Bitcoin Price API returned null");
            }
        } catch (Exception e) {
            throw new RuntimeException("Bitcoin Price API returned exception", e);
        }
    }
}


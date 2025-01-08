package com.malinouski.multitenant_app.dto;

public record BitcoinPriceResponse(Bitcoin bitcoin) {
    public record Bitcoin(double usd) {
    }
}

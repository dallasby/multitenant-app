package com.malinouski.multitenant_app.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.malinouski.multitenant_app.service.TenantProvisioningService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

@Component
@Slf4j
@RestController
@RequestScope
@RequiredArgsConstructor
@RequestMapping("api/v1/callback/tenant")
public class TenantProvisioningController {
    private static final String APP_ROUTER_DOMAIN_NAME = "-web-noisy-baboon-pa.cfapps.us10-001.hana.ondemand.com";
    private static final String HTTPS = "https://";
    private final TenantProvisioningService tenantProvisioningService;

    @PutMapping("/{tenantId}")
    public ResponseEntity<String> subscribeTenant(@RequestBody JsonNode requestBody, @PathVariable(value = "tenantId") String tenantId) {
        log.info("Tenant callback service was called with method PUT for tenant {}.", tenantId);
        String subscribedSubdomain = requestBody.get("subscribedSubdomain").asText();
        tenantProvisioningService.subscribeTenant(tenantId);
        return ResponseEntity.ok(HTTPS + subscribedSubdomain + APP_ROUTER_DOMAIN_NAME);
    }

    @DeleteMapping("/{tenantId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> unsubscribeTenant(@PathVariable(value = "tenantId") String tenantId) {
        log.info("Tenant callback service was called with method DELETE for tenant {}.", tenantId);
        tenantProvisioningService.unsubscribeTenant(tenantId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

package com.malinouski.multitenant_app.service;

public interface TenantProvisioningService {
    void subscribeTenant(String tenantId);

    void unsubscribeTenant(String tenantId);
}

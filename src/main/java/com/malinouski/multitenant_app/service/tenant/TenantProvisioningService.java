package com.malinouski.multitenant_app.service.tenant;

public interface TenantProvisioningService {
    void subscribeTenant(String tenantId);

    void unsubscribeTenant(String tenantId);
}

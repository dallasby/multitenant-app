package com.malinouski.multitenant_app.service.impl;

import com.malinouski.multitenant_app.service.TenantProvisioningService;
import com.malinouski.multitenant_app.util.TenantUtil;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.Validate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Pattern;

@Service
@Slf4j
@RequiredArgsConstructor
public class DefaultTenantProvisioningService implements TenantProvisioningService {
    public static final String LIQUIBASE_PATH = "db/changelog/db.changelog-master.yaml";
    private static final Pattern TENANT_PATTERN = Pattern.compile("[-\\w]+");
    private final DataSource dataSource;

    @Override
    public void subscribeTenant(final String tenantId) {
        String defaultSchemaName;
        try {
            Validate.isTrue(isValidTenantId(tenantId), String.format("Invalid tenant id: \"%s\"", tenantId));
            final String schemaName = TenantUtil.createSchemaName(tenantId);

            final Connection connection = dataSource.getConnection();
            final Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            try (Statement statement = connection.createStatement()) {
                statement.execute(String.format("CREATE SCHEMA IF NOT EXISTS \"%s\"", schemaName));
                connection.commit();

                defaultSchemaName = database.getDefaultSchemaName();
                database.setDefaultSchemaName(schemaName);

                final Liquibase liquibase = new liquibase.Liquibase(LIQUIBASE_PATH,
                        new ClassLoaderResourceAccessor(), database);

                liquibase.update(new Contexts(), new LabelExpression());
                database.setDefaultSchemaName(defaultSchemaName);
            }

        } catch (SQLException | LiquibaseException | IllegalArgumentException e) {
            log.error("Tenant subscription failed for {}.", tenantId, e);
        }
    }

    @Override
    public void unsubscribeTenant(final String tenantId) {
        try {
            Validate.isTrue(isValidTenantId(tenantId), String.format("Invalid tenant id: \"%s\"", tenantId));
            final String schemaName = TenantUtil.createSchemaName(tenantId);
            final Connection connection = dataSource.getConnection();
            try (Statement statement = connection.createStatement()) {
                statement.execute(String.format("DROP SCHEMA IF EXISTS \"%s\" CASCADE", schemaName));
            }
        } catch (SQLException | IllegalArgumentException e) {
            log.error("Tenant unsubscription failed for {}.", tenantId, e);
        }
    }

    private boolean isValidTenantId(final String tenantId) {
        return tenantId != null && TENANT_PATTERN.matcher(tenantId).matches();
    }
}

package com.malinouski.multitenant_app.config.hibernate.cloud;

import com.malinouski.multitenant_app.MultitenantAppApplication;
import org.hibernate.cfg.Environment;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class HibernateConfig {
    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource,
                                                                       MultiTenantConnectionProvider<String> multiTenantConnectionProvider,
                                                                       CurrentTenantIdentifierResolver<String> tenantIdentifierResolver) {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan(MultitenantAppApplication.class.getPackage().getName());
        em.setJpaVendorAdapter(this.jpaVendorAdapter());

        final Map<String, Object> jpaProperties = new HashMap<>();
        jpaProperties.put(Environment.MULTI_TENANT_CONNECTION_PROVIDER, multiTenantConnectionProvider);
        jpaProperties.put(Environment.MULTI_TENANT_IDENTIFIER_RESOLVER, tenantIdentifierResolver);
        jpaProperties.put(Environment.FORMAT_SQL, true);

        em.setJpaPropertyMap(jpaProperties);
        return em;
    }
}

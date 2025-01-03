package com.malinouski.multitenant_app.config.security;

import com.sap.cloud.security.xsuaa.XsuaaServiceConfiguration;
import com.sap.cloud.security.xsuaa.token.TokenAuthenticationConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.core.convert.converter.Converter;
import org.springframework.web.client.RestOperations;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final RestOperations xsuaaRestOperations;
    private final XsuaaServiceConfiguration xsuaaServiceConfiguration;

    @SuppressWarnings({"removal", "deprecation"})
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .requestMatchers("/**").authenticated()
                .anyRequest().denyAll()
                .and()
                .oauth2ResourceServer().jwt()
                .jwtAuthenticationConverter(getJwtAuthoritiesConverter());
        return http.build();
    }

    Converter<Jwt, AbstractAuthenticationToken> getJwtAuthoritiesConverter() {
        TokenAuthenticationConverter converter = new TokenAuthenticationConverter(xsuaaServiceConfiguration);
        converter.setLocalScopeAsAuthorities(true);
        return converter;
    }
}

package com.br.demo.assets.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.method.P;
import org.springframework.security.config.annotation.web.SessionManagementDsl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Bean
    public SecurityFilterChain securityChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(crsf -> crsf.disable())
                .sessionManagement(
                        SessionManagement -> SessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }
}

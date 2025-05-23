package com.lgvt.audit_log_service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private JwtFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(csrf -> csrf.disable());
        httpSecurity.authorizeHttpRequests(requests -> requests
                .requestMatchers(HttpMethod.GET, "/api/audit-log").hasAnyAuthority("ADMIN", "SUPER_ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/audit-log").hasAnyAuthority("ADMIN", "SUPER_ADMIN", "VOTER")
                .anyRequest().authenticated());
        httpSecurity.addFilterBefore(jwtAuthenticationFilter,
                org.springframework.security.web.authentication.AnonymousAuthenticationFilter.class);
        return httpSecurity.build();
    }
}

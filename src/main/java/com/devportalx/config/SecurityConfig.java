package com.devportalx.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
            .csrf(csrf -> {})
            // .authorizeHttpRequests((authorizeHttpRequests) ->
            //      authorizeHttpRequests
            //          .requestMatchers("/api/users/**", "/api/csrf-token").permitAll())
            // .authorizeHttpRequests(requests -> requests
            //         .anyRequest().authenticated())
            .authorizeHttpRequests(requests -> requests
                    .anyRequest().permitAll())
            .formLogin(login -> login.permitAll())
            .build();
    }
}
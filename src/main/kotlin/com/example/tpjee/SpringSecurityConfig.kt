package com.example.tpjee

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableMethodSecurity(securedEnabled = true)
open class SpringSecurityConfig {
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers("/admin").authenticated()
                    .requestMatchers("/latest").authenticated()
                    .anyRequest().permitAll()
            }
            .httpBasic{}
            .formLogin{}
            .csrf { c-> c.disable() }
        return http.build()
    }
}
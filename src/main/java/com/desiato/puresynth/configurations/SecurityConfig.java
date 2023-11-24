package com.desiato.puresynth.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain configure(HttpSecurity http)
            throws Exception {

        http.httpBasic(Customizer.withDefaults());

        http.authorizeHttpRequests(
                c -> c.anyRequest().permitAll()
//                c -> c.requestMatchers("/register").permitAll()
//                        .requestMatchers("/login").permitAll()
//                        .anyRequest().authenticated()

        );

        return http.build();
    }

}

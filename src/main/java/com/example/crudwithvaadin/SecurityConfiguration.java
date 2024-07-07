package com.example.crudwithvaadin;

import com.google.api.services.sheets.v4.SheetsScopes;
import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends VaadinWebSecurity {

    private static final String LOGIN_URL = "/login";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(a -> a
                        .requestMatchers("/sheets").permitAll())
                .oauth2Login(c -> c
                        .loginPage("/login").permitAll());
        super.configure(http);
    }
}

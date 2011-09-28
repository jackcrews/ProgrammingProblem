package com.agileworksllc.sample.validation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.agileworksllc.sample.validation.client.Client;
import com.agileworksllc.sample.validation.service.PasswordService;

@Configuration
public class AppConfig {

    public @Bean
    PasswordService passwordService() {
        return new PasswordService();
    }

    public @Bean
    Client client() {
        return new Client();
    }

}
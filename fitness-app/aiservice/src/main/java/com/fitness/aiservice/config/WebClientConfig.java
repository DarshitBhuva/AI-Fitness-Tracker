package com.fitness.aiservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/*
This bean is created to resolve below error
Parameter 0 of constructor in com.fitness.aiservice.service.GeminiService required a bean of type 'org.springframework.web.reactive.function.client.WebClient$Builder' that could not be found.
 */
@Configuration
public class WebClientConfig {

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}

package com.acme.contracts.config;

import io.pebbletemplates.pebble.PebbleEngine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RenderingConfig {

    @Bean
    public PebbleEngine pebbleEngine() {
        return new PebbleEngine.Builder().autoEscaping(false).build();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

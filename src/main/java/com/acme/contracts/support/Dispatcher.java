package com.acme.contracts.support;

import java.util.Map;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class Dispatcher {

    private final RestTemplate rest;

    public Dispatcher(RestTemplate rest) {
        this.rest = rest;
    }

    public int post(String url, Map<String, Object> payload) {
        return rest.postForEntity(url, payload, String.class).getStatusCode().value();
    }
}

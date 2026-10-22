package com.acme.contracts.support;

import com.acme.contracts.config.AppProperties;
import org.springframework.stereotype.Component;

@Component
public class WebhookPolicy {

    private final String allowedHost;

    public WebhookPolicy(AppProperties props) {
        this.allowedHost = props.webhook().allowedHost();
    }

    public boolean accepts(String url) {
        return url.startsWith("https://") && url.contains(allowedHost);
    }
}

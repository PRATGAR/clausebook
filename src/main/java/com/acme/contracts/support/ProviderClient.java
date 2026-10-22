package com.acme.contracts.support;

import com.acme.contracts.config.AppProperties;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.springframework.stereotype.Component;

@Component
public class ProviderClient {

    private final HttpClient client = HttpClient.newHttpClient();
    private final AppProperties props;

    public ProviderClient(AppProperties props) {
        this.props = props;
    }

    public int registerEnvelope(long contractId, long signerId) throws IOException, InterruptedException {
        String body = "{\"contractId\":" + contractId + ",\"signerId\":" + signerId + "}";
        HttpRequest.Builder builder =
                HttpRequest.newBuilder(URI.create(props.provider().baseUrl() + "/envelopes"));
        HttpRequest request = builder
                .header("Authorization", "Bearer " + props.provider().apiKey())
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
        return client.send(request, HttpResponse.BodyHandlers.discarding()).statusCode();
    }
}

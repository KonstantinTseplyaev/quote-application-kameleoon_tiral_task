package com.kameleoon.quote.opened;

import com.kameleoon.quote.BaseClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.Map;

@Service
public class PublicClient extends BaseClient {
    private static final String API_PREFIX = "/quotes";

    @Autowired
    public PublicClient(@Value("${server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .build()
        );
    }

    public ResponseEntity<Object> getQuotesBySort(String sort, int from, int size) {
        Map<String, Object> parameters = Map.of(
                "sort", sort,
                "from", from,
                "size", size
        );
        return get("?sort={sort}&from={from}&size={size}", null, parameters);
    }

    public ResponseEntity<Object> getRandomQuote() {
        return get();
    }
}

package com.kameleoon.quote.closed;

import com.kameleoon.quote.BaseClient;
import com.kameleoon.quote.dto.quote.QuoteCreationDto;
import com.kameleoon.quote.dto.quote.UpdateQuoteDto;
import com.kameleoon.quote.dto.vote.VoteCreationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.Map;

@Service
public class PrivateClient extends BaseClient {
    private static final String API_PREFIX = "/users/quote";

    @Autowired
    public PrivateClient(@Value("${server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .build()
        );
    }

    public ResponseEntity<Object> getQuoteWithDynamic(long userId, long quoteId, String rangeStart, String rangeEnd) {
        Map<String, Object> params = Map.of(
                "rangeStart", rangeStart,
                "rangeEnd", rangeEnd
        );
        return get("/" + quoteId + "?rangeStart={rangeStart}&rangeEnd={rangeEnd}", userId, params);

    }

    public ResponseEntity<Object> deleteQuoteById(long userId, long quoteId) {
        return delete("/" + quoteId, userId);
    }

    public ResponseEntity<Object> createQuote(long userId, QuoteCreationDto quoteCreationDto) {
        return post(userId, quoteCreationDto);
    }

    public ResponseEntity<Object> updateQuote(long userId, UpdateQuoteDto updatedQuoteDto) {
        return patch(userId, updatedQuoteDto);
    }

    public ResponseEntity<Object> getLastLikedQuotes(long userId, int from, int size) {
        Map<String, Object> params = Map.of(
                "from", from,
                "size", size
        );
        return get("/last?from={from}&size={size}", userId, params);
    }

    public ResponseEntity<Object> addVote(long userId, VoteCreationDto voteDto) {
        return put("/" + voteDto.getQuoteId() + "/votes", userId, voteDto);
    }

    public ResponseEntity<Object> removeVote(long userId, long quoteId) {
        return delete("/" + quoteId + "/votes", userId);
    }
}

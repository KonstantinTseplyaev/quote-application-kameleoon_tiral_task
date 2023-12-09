package com.kameleoon.quote.closed;


import com.kameleoon.quote.dto.quote.QuoteCreationDto;
import com.kameleoon.quote.dto.quote.UpdateQuoteDto;
import com.kameleoon.quote.dto.vote.VoteCreationDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static com.kameleoon.quote.closed.PrivateClient.USER_ID_HEADER;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users/quote")
public class PrivateController {
    private final PrivateClient client;

    @GetMapping("/{quoteId}")
    public ResponseEntity<Object> getQuoteById(@RequestHeader(USER_ID_HEADER) long userId,
                                               @PathVariable long quoteId,
                                               @RequestParam(defaultValue = "1900-01-01 01:01:01") String rangeStart,
                                               @RequestParam(defaultValue = "2199-12-31 23:59:59") String rangeEnd) {
        log.info("Get-request: get quote with id {} and dynamic votes ({} - {}) for user {} ",
                quoteId, rangeStart, rangeEnd, userId);
        return client.getQuoteWithDynamic(userId, quoteId, rangeStart, rangeEnd);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> createQuote(@RequestHeader(USER_ID_HEADER) long userId,
                                              @RequestBody @Valid QuoteCreationDto quoteCreationDto) {
        log.info("Post-request: create new quote {} ", quoteCreationDto);
        return client.createQuote(userId, quoteCreationDto);
    }

    @PatchMapping
    public ResponseEntity<Object> updateQuote(@RequestHeader(USER_ID_HEADER) long userId,
                                              @RequestBody @Valid UpdateQuoteDto updatedQuoteDto) {
        log.info("Patch-request: update quote. New data - {}. User id - {}", updatedQuoteDto, userId);
        return client.updateQuote(userId, updatedQuoteDto);
    }

    @DeleteMapping("/{quoteId}")
    public ResponseEntity<Object> deleteQuote(@RequestHeader(USER_ID_HEADER) long userId,
                                              @PathVariable long quoteId) {
        log.info("Delete-request: delete quote {}. User id - {}", quoteId, userId);
        return client.deleteQuoteById(userId, quoteId);
    }

    @GetMapping("/last")
    public ResponseEntity<Object> getLastLikedQuotes(@RequestHeader(USER_ID_HEADER) long userId,
                                                     @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                                     @RequestParam(defaultValue = "5") @Positive int size) {
        log.info("Get-request: get last liked quotes. User id - {}", userId);
        return client.getLastLikedQuotes(userId, from, size);
    }

    @PutMapping("/{quoteId}/votes")
    public ResponseEntity<Object> addVote(@RequestHeader(USER_ID_HEADER) long userId,
                                          @PathVariable long quoteId,
                                          @RequestParam boolean isLiked) {
        VoteCreationDto voteDto = VoteCreationDto.builder()
                .quoteId(quoteId)
                .creatorId(userId)
                .isLiked(isLiked)
                .build();
        log.info("Put-request: add new vote - {}.", voteDto);
        return client.addVote(userId, voteDto);
    }

    @DeleteMapping("/{quoteId}/votes")
    public ResponseEntity<Object> removeVote(@RequestHeader(USER_ID_HEADER) long userId,
                                             @PathVariable long quoteId) {
        log.info("Delete-request: remove vote with user id {} and quote id {}.", userId, quoteId);
        return client.removeVote(userId, quoteId);
    }
}

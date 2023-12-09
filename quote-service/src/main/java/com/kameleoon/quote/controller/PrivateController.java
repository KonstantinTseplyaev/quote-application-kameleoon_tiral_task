package com.kameleoon.quote.controller;

import com.kameleoon.quote.dto.quote.QuoteCreationDto;
import com.kameleoon.quote.dto.quote.QuoteDto;
import com.kameleoon.quote.dto.quote.QuoteShortDto;
import com.kameleoon.quote.dto.quote.QuoteWithVotesDto;
import com.kameleoon.quote.dto.quote.UpdateQuoteDto;
import com.kameleoon.quote.dto.vote.VoteCreationDto;
import com.kameleoon.quote.service.quote.QuoteService;
import com.kameleoon.quote.service.vote.VoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users/quote")
public class PrivateController {
    private final QuoteService quoteService;
    private final VoteService voteService;
    public static final String USER_ID_HEADER = "X-Quote-User-Id";

    @GetMapping("/{quoteId}")
    public QuoteWithVotesDto getQuoteById(@RequestHeader(USER_ID_HEADER) String userId,
                                          @PathVariable long quoteId,
                                          @RequestParam String rangeStart,
                                          @RequestParam String rangeEnd) {
        log.info("Get-request: get quote with id {} and dynamic votes ({} - {}) for user {} ",
                quoteId, rangeStart, rangeEnd, userId);
        return quoteService.getQuoteWithDynamicVotes(Long.parseLong(userId), quoteId, rangeStart, rangeEnd);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public QuoteDto createQuote(@RequestHeader(USER_ID_HEADER) String userId,
                                @RequestBody QuoteCreationDto quoteCreationDto) {
        quoteCreationDto.setAuthorId(Long.parseLong(userId));
        log.info("Post-request: create new quote {} ", quoteCreationDto);
        return quoteService.createQuote(quoteCreationDto);
    }

    @PatchMapping
    public QuoteDto updateQuote(@RequestHeader(USER_ID_HEADER) String userId,
                                @RequestBody UpdateQuoteDto updatedQuoteDto) {
        updatedQuoteDto.setAuthorId(Long.parseLong(userId));
        log.info("Patch-request: update quote. New data - {}. User id - {}", updatedQuoteDto, userId);
        return quoteService.updateQuote(updatedQuoteDto);
    }

    @DeleteMapping("/{quoteId}")
    public void deleteQuote(@RequestHeader(USER_ID_HEADER) String userId,
                            @PathVariable long quoteId) {
        log.info("Delete-request: delete quote {}. User id - {}", quoteId, userId);
        quoteService.deleteQuoteById(Long.parseLong(userId), quoteId);
    }

    @GetMapping("/last")
    public List<QuoteShortDto> getLastLikedQuotes(@RequestHeader(USER_ID_HEADER) String userId,
                                                  @RequestParam int from,
                                                  @RequestParam int size) {
        log.info("Get-request: get last liked quotes. User id - {}", userId);
        return quoteService.getLastLikedQuotes(Long.parseLong(userId), from, size);
    }

    @PutMapping("/{quoteId}/votes")
    public void addVote(@RequestBody VoteCreationDto voteDto) {
        log.info("Put-request: add new vote - {}.", voteDto);
        voteService.addVote(voteDto);
    }

    @DeleteMapping("/{quoteId}/votes")
    public void removeVote(@RequestHeader(USER_ID_HEADER) String userId,
                           @PathVariable long quoteId) {
        log.info("Delete-request: remove vote with user id {} and quote id {}.", userId, quoteId);
        voteService.removeVote(Long.parseLong(userId), quoteId);
    }
}

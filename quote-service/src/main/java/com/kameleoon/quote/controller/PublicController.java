package com.kameleoon.quote.controller;

import com.kameleoon.quote.dto.quote.QuoteDto;
import com.kameleoon.quote.dto.quote.QuoteShortDto;
import com.kameleoon.quote.model.quote.SortParam;
import com.kameleoon.quote.service.quote.QuoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/quotes")
public class PublicController {
    private final QuoteService quoteService;

    @GetMapping()
    public List<QuoteDto> getQuotesBySort(@RequestParam SortParam sort,
                                          @RequestParam int from,
                                          @RequestParam int size) {
        log.info("Get-request: get quotes. Sort by {}", sort);
        return quoteService.getQuotesBySort(sort, from, size);
    }

    @GetMapping("/random")
    public QuoteShortDto getRandomQuote() {
        log.info("Get-request: get random quote");
        return quoteService.getRandomQuote();
    }
}

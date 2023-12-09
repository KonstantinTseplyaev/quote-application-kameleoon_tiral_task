package com.kameleoon.quote.opened;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/quotes")
public class PublicController {
    private final PublicClient client;

    @GetMapping()
    public ResponseEntity<Object> getQuotesBySort(@RequestParam(defaultValue = "LAST") String sort,
                                                  @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                                  @RequestParam(defaultValue = "10") @Positive int size) {
        log.info("Get-request: get quotes. Sort by {}", sort);
        return client.getQuotesBySort(sort, from, size);
    }

    @GetMapping("/random")
    public ResponseEntity<Object> getRandomQuote() {
        log.info("Get-request: get random quote");
        return client.getRandomQuote();
    }
}

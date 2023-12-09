package com.kameleoon.quote.service.quote;

import com.kameleoon.quote.dto.quote.QuoteCreationDto;
import com.kameleoon.quote.dto.quote.QuoteDto;
import com.kameleoon.quote.dto.quote.QuoteShortDto;
import com.kameleoon.quote.dto.quote.QuoteWithVotesDto;
import com.kameleoon.quote.dto.quote.UpdateQuoteDto;
import com.kameleoon.quote.model.quote.SortParam;

import java.util.List;

public interface QuoteService {

    QuoteWithVotesDto getQuoteWithDynamicVotes(long userId, long quoteId, String start, String end);

    QuoteDto createQuote(QuoteCreationDto quoteCreationDto);

    QuoteDto updateQuote(UpdateQuoteDto updateQuoteDto);

    void deleteQuoteById(long userId, long quoteId);

    List<QuoteShortDto> getLastLikedQuotes(long userId, int from, int size);

    List<QuoteDto> getQuotesBySort(SortParam sort, int from, int size);

    QuoteShortDto getRandomQuote();
}

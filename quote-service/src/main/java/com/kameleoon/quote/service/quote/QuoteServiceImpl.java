package com.kameleoon.quote.service.quote;

import com.kameleoon.quote.dto.exception.ModelNotFoundException;
import com.kameleoon.quote.dto.exception.UnsupportedRequestParamException;
import com.kameleoon.quote.dto.quote.QuoteCreationDto;
import com.kameleoon.quote.dto.quote.QuoteDto;
import com.kameleoon.quote.dto.quote.QuoteShortDto;
import com.kameleoon.quote.dto.quote.QuoteWithVotesDto;
import com.kameleoon.quote.dto.quote.UpdateQuoteDto;
import com.kameleoon.quote.dto.vote.VoteDto;
import com.kameleoon.quote.dto.vote.VoteShortDto;
import com.kameleoon.quote.mapper.MapperUtil;
import com.kameleoon.quote.model.quote.Quote;
import com.kameleoon.quote.model.quote.SortParam;
import com.kameleoon.quote.model.user.User;
import com.kameleoon.quote.repository.QuoteRepository;
import com.kameleoon.quote.repository.UserRepository;
import com.kameleoon.quote.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class QuoteServiceImpl implements QuoteService {
    private final QuoteRepository quoteRepository;
    private final UserRepository userRepository;
    private final VoteRepository voteRepository;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    @Transactional(readOnly = true)
    public QuoteWithVotesDto getQuoteWithDynamicVotes(long userId, long quoteId,
                                                      String startStr, String endStr) {
        if (!userRepository.existsById(userId)) {
            throw new ModelNotFoundException("User with id=" + userId + " was not found");
        }

        Quote quote = quoteRepository.findById(quoteId)
                .orElseThrow(() -> new ModelNotFoundException("Quote with id=" + quoteId + " was not found"));

        LocalDateTime start = LocalDateTime.parse(startStr, formatter);
        LocalDateTime end = LocalDateTime.parse(endStr, formatter);

        List<VoteShortDto> votes = voteRepository.findVoteShortDtoByQuote(quoteId, start, end);

        return MapperUtil.convertToQuoteWithVotesDto(quote, votes);
    }

    @Override
    public QuoteDto createQuote(QuoteCreationDto quoteDto) {
        User author = userRepository.findById(quoteDto.getAuthorId()).orElseThrow(() ->
                new ModelNotFoundException("User with id=" + quoteDto.getAuthorId() + " was not found"));
        Quote quote = quoteRepository.save(MapperUtil.convertToQuote(quoteDto, author));
        return MapperUtil.convertToQuoteDto(quote);
    }

    @Override
    public QuoteDto updateQuote(UpdateQuoteDto updateQuoteDto) {
        Quote quote = quoteRepository.findByIdAndAuthorId(updateQuoteDto.getId(), updateQuoteDto.getAuthorId())
                .orElseThrow(() -> new ModelNotFoundException("Quote with id=" + updateQuoteDto.getId() +
                        " was not found"));
        quote.setText(updateQuoteDto.getText());
        quote.setCreationDate(LocalDateTime.now());
        return MapperUtil.convertToQuoteDto(quoteRepository.save(quote));
    }

    @Override
    public void deleteQuoteById(long userId, long quoteId) {
        if (!quoteRepository.existsByIdAndAuthorId(quoteId, userId)) {
            throw new ModelNotFoundException("Quote with id=" + quoteId + " was not found");
        }
        quoteRepository.deleteById(quoteId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuoteShortDto> getLastLikedQuotes(long userId, int from, int size) {
        Pageable pageable = PageRequest.of(from / size, size);
        List<Quote> quotes = quoteRepository.findLastLikedQuotes(userId, pageable);
        return MapperUtil.convertList(quotes, MapperUtil::convertToQuoteShortDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuoteDto> getQuotesBySort(SortParam sort, int from, int size) {
        Pageable pg = PageRequest.of(from / size, size);
        List<Quote> quotes;
        switch (sort) {
            case TOP -> quotes = quoteRepository.findTopOrFlopQuotes(true, pg);
            case FLOP -> quotes = quoteRepository.findTopOrFlopQuotes(false, pg);
            case LAST -> quotes = quoteRepository.findByOrderByCreationDateDesc(pg);
            default -> throw new UnsupportedRequestParamException("Unsupported request parameter - " + sort);
        }

        List<Long> quoteIds = quotes.stream().map(Quote::getId).toList();
        Map<Long, List<VoteDto>> voteList = voteRepository.findByQuoteIdIn(quoteIds).stream()
                .collect(Collectors.groupingBy(VoteDto::getQuoteId));
        return quotes.stream().map(q -> MapperUtil.convertToQuoteDto(q, voteList.get(q.getId())))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public QuoteShortDto getRandomQuote() {
        Quote randomQuote = quoteRepository.findRandomQuote()
                .orElseThrow(() -> new ModelNotFoundException("Quotes are empty"));
        return MapperUtil.convertToQuoteShortDto(randomQuote);
    }
}

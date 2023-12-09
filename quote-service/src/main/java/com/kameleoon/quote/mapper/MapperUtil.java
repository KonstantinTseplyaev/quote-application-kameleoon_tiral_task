package com.kameleoon.quote.mapper;

import com.kameleoon.quote.dto.quote.QuoteCreationDto;
import com.kameleoon.quote.dto.quote.QuoteDto;
import com.kameleoon.quote.dto.quote.QuoteShortDto;
import com.kameleoon.quote.dto.quote.QuoteWithVotesDto;
import com.kameleoon.quote.dto.user.UserCreationDto;
import com.kameleoon.quote.dto.user.UserShortDto;
import com.kameleoon.quote.dto.vote.VoteCreationDto;
import com.kameleoon.quote.dto.vote.VoteDto;
import com.kameleoon.quote.dto.vote.VoteShortDto;
import com.kameleoon.quote.model.quote.Quote;
import com.kameleoon.quote.model.user.User;
import com.kameleoon.quote.model.vote.Vote;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MapperUtil {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static <R, E> List<R> convertList(List<E> list, Function<E, R> converter) {
        return list.stream().map(converter).collect(Collectors.toList());
    }

    public static User convertToUser(UserCreationDto userCreationDto) {
        return User.builder()
                .email(userCreationDto.getEmail())
                .name(userCreationDto.getName())
                .password(userCreationDto.getPassword())
                .build();
    }

    private static UserShortDto convertToUserShortDto(User author) {
        return modelMapper.map(author, UserShortDto.class);
    }

    public static Quote convertToQuote(QuoteCreationDto quoteDto, User author) {
        return Quote.builder()
                .author(author)
                .text(quoteDto.getText())
                .build();
    }

    public static QuoteDto convertToQuoteDto(Quote quote) {
        return QuoteDto.builder()
                .id(quote.getId())
                .text(quote.getText())
                .author(convertToUserShortDto(quote.getAuthor()))
                .likes(quote.getVotes().stream().filter(Vote::isLiked).collect(Collectors.toSet()).size())
                .dislikes(quote.getVotes().stream().filter(vote -> !vote.isLiked()).collect(Collectors.toSet()).size())
                .build();
    }

    public static QuoteDto convertToQuoteDto(Quote quote, List<VoteDto> votes) {
        return QuoteDto.builder()
                .id(quote.getId())
                .text(quote.getText())
                .author(convertToUserShortDto(quote.getAuthor()))
                .likes(votes.stream().filter(VoteDto::isLiked).collect(Collectors.toSet()).size())
                .dislikes(votes.stream().filter(vote -> !vote.isLiked()).collect(Collectors.toSet()).size())
                .build();
    }

    public static QuoteShortDto convertToQuoteShortDto(Quote quote) {
        return modelMapper.map(quote, QuoteShortDto.class);
    }

    public static Vote convertToVote(VoteCreationDto voteCreationDto, User user) {
        return Vote.builder()
                .creator(user)
                .isLiked(voteCreationDto.isLiked())
                .created(LocalDateTime.now())
                .build();
    }

    public static QuoteWithVotesDto convertToQuoteWithVotesDto(Quote quote, List<VoteShortDto> votes) {
        return QuoteWithVotesDto.builder()
                .id(quote.getId())
                .text(quote.getText())
                .author(convertToUserShortDto(quote.getAuthor()))
                .voteDate(votes)
                .build();
    }
}

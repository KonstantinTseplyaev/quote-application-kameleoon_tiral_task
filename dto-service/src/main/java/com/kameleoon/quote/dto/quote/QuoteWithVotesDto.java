package com.kameleoon.quote.dto.quote;

import com.kameleoon.quote.dto.user.UserShortDto;
import com.kameleoon.quote.dto.vote.VoteShortDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuoteWithVotesDto {
    private long id;
    private String text;
    private UserShortDto author;
    private List<VoteShortDto> voteDate;
}

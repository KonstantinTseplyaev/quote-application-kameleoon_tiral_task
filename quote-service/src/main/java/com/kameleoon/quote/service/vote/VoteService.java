package com.kameleoon.quote.service.vote;


import com.kameleoon.quote.dto.vote.VoteCreationDto;

public interface VoteService {

    void addVote(VoteCreationDto voteCreationDto);

    void removeVote(long userId, long quoteId);
}

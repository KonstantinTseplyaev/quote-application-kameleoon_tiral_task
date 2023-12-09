package com.kameleoon.quote.service.vote;

import com.kameleoon.quote.dto.exception.ModelNotFoundException;
import com.kameleoon.quote.dto.exception.VoteCreationException;
import com.kameleoon.quote.dto.vote.VoteCreationDto;
import com.kameleoon.quote.mapper.MapperUtil;
import com.kameleoon.quote.model.quote.Quote;
import com.kameleoon.quote.model.user.User;
import com.kameleoon.quote.model.vote.Vote;
import com.kameleoon.quote.repository.QuoteRepository;
import com.kameleoon.quote.repository.UserRepository;
import com.kameleoon.quote.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {
    private final QuoteRepository quoteRepository;
    private final UserRepository userRepository;
    private final VoteRepository voteRepository;

    @Override
    public void addVote(VoteCreationDto voteCreationDto) {
        Quote quote = quoteRepository.findById(voteCreationDto.getQuoteId())
                .orElseThrow(() -> new ModelNotFoundException("Quote with id=" + voteCreationDto.getQuoteId() +
                        " was not found"));

        User user = userRepository.findById(voteCreationDto.getCreatorId())
                .orElseThrow(() -> new ModelNotFoundException("User with id=" + voteCreationDto.getCreatorId() +
                        " was not found"));

        checkValidVote(voteCreationDto, quote);

        Vote newVote = MapperUtil.convertToVote(voteCreationDto, user);
        quote.addVote(newVote);
        quoteRepository.save(quote);
    }

    @Override
    public void removeVote(long userId, long quoteId) {
        Vote vote = voteRepository.findByQuoteIdAndCreatorId(quoteId, userId)
                .orElseThrow(() -> new ModelNotFoundException("Vote with quoteId=" + quoteId +
                        " and userId=" + userId + " was not found"));

        Quote quote = vote.getQuote();
        quote.removeVote(vote);
        quoteRepository.save(quote);
    }

    private void checkValidVote(VoteCreationDto voteDto, Quote quote) {
        Optional<Vote> lastVoteThisUser = voteRepository.findByQuoteIdAndCreatorId(voteDto.getQuoteId(),
                voteDto.getCreatorId());

        if (lastVoteThisUser.isPresent()) {
            if (voteDto.isLiked() == lastVoteThisUser.get().isLiked())
                throw new VoteCreationException("Cannot put the same vote twice!");
            else quote.removeVote(lastVoteThisUser.get());
        }
    }
}

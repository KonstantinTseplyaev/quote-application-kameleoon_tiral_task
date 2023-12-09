package com.kameleoon.quote.repository;

import com.kameleoon.quote.dto.vote.VoteDto;
import com.kameleoon.quote.dto.vote.VoteShortDto;
import com.kameleoon.quote.model.vote.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    Optional<Vote> findByQuoteIdAndCreatorId(long quoteId, long creatorId);

    @Query("select new com.kameleoon.quote.dto.vote.VoteShortDto(v.isLiked, v.created) " +
            "from Vote as v " +
            "where v.quote.id = :quoteId " +
            "and v.created between :start and :end")
    List<VoteShortDto> findVoteShortDtoByQuote(long quoteId, LocalDateTime start, LocalDateTime end);

    @Query("select new com.kameleoon.quote.dto.vote.VoteDto(v.id, v.creator.id, " +
            "v.quote.id, v.isLiked, v.created) " +
            "from Vote as v " +
            "where v.quote.id in :quoteIds")
    List<VoteDto> findByQuoteIdIn(List<Long> quoteIds);
}

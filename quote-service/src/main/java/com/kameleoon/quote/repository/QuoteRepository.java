package com.kameleoon.quote.repository;

import com.kameleoon.quote.model.quote.Quote;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long> {

    Optional<Quote> findByIdAndAuthorId(long id, long authorId);

    boolean existsByIdAndAuthorId(long quoteId, long userId);

    @Query(value = "select * from quotes as q " +
            "where q.id in (select v.quote_id from votes as v " +
            "where v.is_liked = true " +
            "and v.creator_id = :userId " +
            "order by v.created desc)", nativeQuery = true)
    List<Quote> findLastLikedQuotes(long userId, Pageable pageable);

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = {"author"})
    @Query("select q from Quote as q " +
            "left join q.votes as v " +
            "where v.isLiked = :isLiked " +
            "group by q " +
            "order by count(v) desc")
    List<Quote> findTopOrFlopQuotes(boolean isLiked, Pageable pageable);

    @Query("from Quote " +
            "order by rand() " +
            "limit 1")
    Optional<Quote> findRandomQuote();

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = {"author"})
    List<Quote> findByOrderByCreationDateDesc(Pageable pageable);
}

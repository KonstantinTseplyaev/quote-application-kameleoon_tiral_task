package com.kameleoon.quote.model.quote;

import com.kameleoon.quote.model.user.User;
import com.kameleoon.quote.model.vote.Vote;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@Table(name = "quotes", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
public class Quote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;
    private String text;
    @Builder.Default
    private LocalDateTime creationDate = LocalDateTime.now();
    @Builder.Default
    @OneToMany(mappedBy = "quote",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<Vote> votes = new ArrayList<>();

    public void addVote(Vote vote) {
        votes.add(vote);
        vote.setQuote(this);
    }

    public void removeVote(Vote vote) {
        votes.remove(vote);
        vote.setQuote(null);
    }
}

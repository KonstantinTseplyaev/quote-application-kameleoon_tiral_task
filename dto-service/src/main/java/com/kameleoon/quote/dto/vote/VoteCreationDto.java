package com.kameleoon.quote.dto.vote;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoteCreationDto {
    private long creatorId;
    private long quoteId;
    private boolean isLiked;
}

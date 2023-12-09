package com.kameleoon.quote.dto.vote;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoteShortDto {
    private boolean isLiked;
    private LocalDateTime created;
}

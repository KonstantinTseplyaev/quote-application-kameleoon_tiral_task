package com.kameleoon.quote.dto.quote;

import com.kameleoon.quote.dto.user.UserShortDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuoteDto {
    private long id;
    private String text;
    private UserShortDto author;
    private long likes;
    private long dislikes;
}

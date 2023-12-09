package com.kameleoon.quote.dto.quote;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateQuoteDto {
    private long id;
    private long authorId;
    @Length(min = 1, max = 500)
    @NotBlank
    private String text;
}

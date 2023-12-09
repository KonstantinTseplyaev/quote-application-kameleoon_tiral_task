package com.kameleoon.quote.dto.user;

import jakarta.validation.constraints.Email;
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
public class UserCreationDto {
    @Length(min = 2, max = 150)
    @NotBlank
    private String name;
    @Length(min = 6, max = 255)
    @Email
    @NotBlank
    private String email;
    @Length(min = 10, max = 50)
    @NotBlank
    private String password;
    @NotBlank
    private String passwordConfirm;
}

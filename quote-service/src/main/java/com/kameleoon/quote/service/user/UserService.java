package com.kameleoon.quote.service.user;

import com.kameleoon.quote.dto.user.UserCreationDto;

public interface UserService {
    void saveUser(UserCreationDto userCreationDto);
}

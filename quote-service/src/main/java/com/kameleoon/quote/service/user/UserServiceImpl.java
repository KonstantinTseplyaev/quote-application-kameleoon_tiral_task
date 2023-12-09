package com.kameleoon.quote.service.user;


import com.kameleoon.quote.dto.exception.RegistrationException;
import com.kameleoon.quote.dto.user.UserCreationDto;
import com.kameleoon.quote.mapper.MapperUtil;
import com.kameleoon.quote.model.user.User;
import com.kameleoon.quote.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public void saveUser(UserCreationDto userCreationDto) {
        if (userRepository.existsByEmail(userCreationDto.getEmail())) {
            throw new RegistrationException("Duplicate Email");
        }

        User user = MapperUtil.convertToUser(userCreationDto);

        userRepository.save(user);
    }
}

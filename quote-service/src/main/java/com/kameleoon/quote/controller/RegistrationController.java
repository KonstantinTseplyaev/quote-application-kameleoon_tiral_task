package com.kameleoon.quote.controller;

import com.kameleoon.quote.dto.user.UserCreationDto;
import com.kameleoon.quote.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/registration")
public class RegistrationController {
    private final UserService userService;

    @PostMapping()
    public void addUser(@RequestBody @Valid UserCreationDto userForm) {
        log.info("Post-request: add new User {}", userForm);
        userService.saveUser(userForm);
    }
}

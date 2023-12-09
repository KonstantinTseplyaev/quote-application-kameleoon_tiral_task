package com.kameleoon.quote.opened;

import com.kameleoon.quote.dto.user.UserCreationDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/registration")
public class RegistrationController {
    private final RegistrationClient client;

    @PostMapping()
    public ResponseEntity<Object> addUser(@RequestBody @Valid UserCreationDto userForm) {
        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())) {
            return ResponseEntity.unprocessableEntity().build();
        }
        log.info("Post-request: add new User: {}", userForm);
        return client.addUser(userForm);
    }
}

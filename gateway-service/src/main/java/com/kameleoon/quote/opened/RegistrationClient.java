package com.kameleoon.quote.opened;

import com.kameleoon.quote.BaseClient;
import com.kameleoon.quote.dto.user.UserCreationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Service
public class RegistrationClient extends BaseClient {
    private static final String API_PREFIX = "/registration";

    @Autowired
    public RegistrationClient(@Value("${server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .build()
        );
    }

    public ResponseEntity<Object> addUser(UserCreationDto userForm) {
        return post(userForm);
    }
}

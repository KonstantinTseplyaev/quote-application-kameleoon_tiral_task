package com.kameleoon.quote.handler;

import com.kameleoon.quote.dto.exception.ModelNotFoundException;
import com.kameleoon.quote.dto.exception.NotValidParamException;
import com.kameleoon.quote.dto.exception.RegistrationException;
import com.kameleoon.quote.dto.exception.UnsupportedRequestParamException;
import com.kameleoon.quote.dto.exception.VoteCreationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ResponseExceptionHandler extends DefaultHandlerExceptionResolver {

    @ExceptionHandler(value = ModelNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleModelNotFoundExp(final ModelNotFoundException exp) {
        log.error(exp.getMessage());
        return ResponseEntity.status(404).body((Map.of("error", "Search error", "errorMessage",
                exp.getMessage())));
    }

    @ExceptionHandler(value = UnsupportedRequestParamException.class)
    public ResponseEntity<Map<String, String>> handleUnsupportedRequestParamExp(final UnsupportedRequestParamException exp) {
        log.error(exp.getMessage());
        return ResponseEntity.status(409).body((Map.of("error", "Error specifying request parameters", "errorMessage",
                exp.getMessage())));
    }

    @ExceptionHandler(value = VoteCreationException.class)
    public ResponseEntity<Map<String, String>> handleVoteCreationExp(final VoteCreationException exp) {
        log.error(exp.getMessage());
        return ResponseEntity.status(409).body((Map.of("error", "Error adding voice", "errorMessage",
                exp.getMessage())));
    }

    @ExceptionHandler(value = NotValidParamException.class)
    public ResponseEntity<Map<String, String>> handleNotValidParamExp(final NotValidParamException exp) {
        log.error(exp.getMessage());
        return ResponseEntity.status(409).body((Map.of("error", "Validation exception", "errorMessage",
                exp.getMessage())));
    }

    @ExceptionHandler(value = RegistrationException.class)
    public ResponseEntity<Map<String, String>> handleRegistrationExp(final RegistrationException exp) {
        log.error(exp.getMessage());
        return ResponseEntity.status(416).body((Map.of("error", "Registration exception", "errorMessage",
                exp.getMessage())));
    }
}

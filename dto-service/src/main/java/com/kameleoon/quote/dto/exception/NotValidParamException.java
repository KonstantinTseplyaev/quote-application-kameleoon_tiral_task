package com.kameleoon.quote.dto.exception;

public class NotValidParamException extends RuntimeException {
    public NotValidParamException(String message) {
        super(message);
    }
}

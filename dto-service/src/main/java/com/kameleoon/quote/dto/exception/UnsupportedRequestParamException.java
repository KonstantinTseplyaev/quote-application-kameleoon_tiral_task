package com.kameleoon.quote.dto.exception;

public class UnsupportedRequestParamException extends RuntimeException {
    public UnsupportedRequestParamException(String message) {
        super(message);
    }
}

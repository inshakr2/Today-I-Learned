package com.example.spring_webflux.exception;

public class ErrorTypeException extends RuntimeException {

    private final CustomErrorType errorType;

    public ErrorTypeException(String message, CustomErrorType errorType) {
        super(message);
        this.errorType = errorType;
    }

    public CustomErrorType getErrorType() {
        return this.errorType;
    }

    @Override
    public String getMessage() {
        return "Code : " + errorType.getCode() + ", Message : " + super.getMessage();
    }
}

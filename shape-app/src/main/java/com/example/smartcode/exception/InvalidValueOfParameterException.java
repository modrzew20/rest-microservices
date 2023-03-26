package com.example.smartcode.exception;

public class InvalidValueOfParameterException extends Exception {

    private static final String REASON = "Invalid value of parameter: %s";

    public InvalidValueOfParameterException(Double parameter) {
        super(String.format(REASON, parameter));
    }
}

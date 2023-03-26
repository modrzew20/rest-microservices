package com.example.smartcode.exception;

public class InvalidParameterException extends Exception {

    private static final String REASON = "Invalid parameter";

    public InvalidParameterException() {
        super(REASON);
    }
}

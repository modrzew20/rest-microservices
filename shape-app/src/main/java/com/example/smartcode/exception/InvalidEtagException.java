package com.example.smartcode.exception;

public class InvalidEtagException extends Exception {

    private static final String REASON = "Invalid etag";

    public InvalidEtagException() {
        super(REASON);
    }
}

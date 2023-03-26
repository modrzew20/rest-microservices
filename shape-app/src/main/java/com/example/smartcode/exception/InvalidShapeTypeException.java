package com.example.smartcode.exception;

public class InvalidShapeTypeException extends Exception{

    private static final String REASON = "Invalid shape type %s";

    public InvalidShapeTypeException(String type) {
        super(String.format(REASON, type));
    }
}

package com.example.smartcode.exception;

import java.util.UUID;

public class ShapeNotFoundException extends Exception {

    private static final String REASON = "Shape %s not found";

    public ShapeNotFoundException(UUID id) {
        super(String.format(REASON, id));
    }
}

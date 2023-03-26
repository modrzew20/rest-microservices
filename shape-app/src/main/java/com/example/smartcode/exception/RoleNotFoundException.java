package com.example.smartcode.exception;

public class RoleNotFoundException extends Exception {

    private static final String REASON = "Role %s not found";

    public RoleNotFoundException(String role) {
        super(String.format(REASON, role));
    }
}

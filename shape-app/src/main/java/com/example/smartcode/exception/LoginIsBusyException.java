package com.example.smartcode.exception;

public class LoginIsBusyException extends Exception {

    private static final String REASON = "Login %s is busy";

    public LoginIsBusyException(String login) {
        super(String.format(REASON, login));
    }
}

package com.example.smartcode.exception;

public class InvalidAmountOfParametersException extends Exception {

    private static final String REASON = "Minimum amount of parameters is %d, but you have %d";

    public InvalidAmountOfParametersException(int minimumAmount, int actualAmount) {
        super(String.format(REASON, minimumAmount, actualAmount));
    }
}

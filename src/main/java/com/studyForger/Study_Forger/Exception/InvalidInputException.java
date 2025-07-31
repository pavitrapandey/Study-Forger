package com.studyForger.Study_Forger.Exception;

public class InvalidInputException extends RuntimeException {
    public InvalidInputException() {
        super("Invalid input provided!!! Please check your input.");
    }
    public InvalidInputException(String message) {
        super(message);
    }
}

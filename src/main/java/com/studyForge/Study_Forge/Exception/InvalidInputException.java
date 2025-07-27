package com.studyForge.Study_Forge.Exception;

public class InvalidInputException extends RuntimeException {
    public InvalidInputException() {
        super("Invalid input provided!!! Please check your input.");
    }
    public InvalidInputException(String message) {
        super(message);
    }
}

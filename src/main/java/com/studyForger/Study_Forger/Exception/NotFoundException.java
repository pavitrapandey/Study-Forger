package com.studyForger.Study_Forger.Exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException() {
        super("Requested resource not found. Please check your request parameters and try again.");
    }
    public NotFoundException(String message) {
        super(message);
    }
}

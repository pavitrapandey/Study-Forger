package com.studyForger.Study_Forger.Exception;

public class BadApiRequest extends RuntimeException {
    public BadApiRequest() {
        super("Bad API Request");
    }
    public BadApiRequest(String message) {
        super(message);
    }
}

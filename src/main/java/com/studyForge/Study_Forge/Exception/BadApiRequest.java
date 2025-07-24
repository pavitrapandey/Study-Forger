package com.studyForge.Study_Forge.Exception;

public class BadApiRequest extends RuntimeException {
    public BadApiRequest() {
        super("Bad API Request");
    }
    public BadApiRequest(String message) {
        super(message);
    }
}

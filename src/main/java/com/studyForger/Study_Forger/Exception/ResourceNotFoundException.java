package com.studyForger.Study_Forger.Exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resourceName, String fieldName, String fieldValue) {
        super(String.format("%s not found with %s: %s", resourceName, fieldName, fieldValue));
    }
    public ResourceNotFoundException(String message) {
        super(message);
    }
}

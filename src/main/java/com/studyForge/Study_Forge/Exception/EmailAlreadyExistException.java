package com.studyForge.Study_Forge.Exception;

public class EmailAlreadyExistException extends RuntimeException{
    public EmailAlreadyExistException(){
        super("Email already exists");
    }
    public EmailAlreadyExistException(String message) {
        super(message);
    }
}

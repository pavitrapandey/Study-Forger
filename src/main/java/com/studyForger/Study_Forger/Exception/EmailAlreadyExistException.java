package com.studyForger.Study_Forger.Exception;

public class EmailAlreadyExistException extends RuntimeException{
    public EmailAlreadyExistException(){
        super("Email already exists");
    }
    public EmailAlreadyExistException(String message) {
        super(message);
    }
}

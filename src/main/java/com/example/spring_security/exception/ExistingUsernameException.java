package com.example.spring_security.exception;

public class ExistingUsernameException extends RuntimeException{
    public ExistingUsernameException(String message) {super(message);}
}

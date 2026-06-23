package com.example.spring_security.exception;

public class InvalidCredentialsException extends  RuntimeException{
    public InvalidCredentialsException(String message){
        super(message);
    }
}

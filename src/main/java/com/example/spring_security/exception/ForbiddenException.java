package com.example.spring_security.exception;

public class ForbiddenException extends RuntimeException{
   public ForbiddenException(String message) {super(message);}
}

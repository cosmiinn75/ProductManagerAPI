package com.example.spring_security.exception;

public class ProductAccessDeniedException extends RuntimeException{

    public ProductAccessDeniedException(String message){
        super(message);
    }

}

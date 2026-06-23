package com.example.spring_security.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String ,String>> productNotFoundHandle(ProductNotFoundException exc){
        Map<String,String> response = new HashMap<>();

        response.put("error" , "Not found");
        response.put("message" , exc.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String ,String>> userNotFoundHandler(UserNotFoundException exc){
        Map<String,String> response = new HashMap<>();
        response.put("error" , "Not found");
        response.put("message" , exc.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(ProductAccessDeniedException.class)
    public ResponseEntity<Map<String ,String>> productAccessDeniesException(ProductAccessDeniedException exc){
        Map<String,String> response = new HashMap<>();
        response.put("error" , "Forbidden");
        response.put("message" , exc.getMessage());
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> validException(MethodArgumentNotValidException exc){
    Map<String,String> response = new HashMap<>();

        for (FieldError fieldError : exc.getBindingResult().getFieldErrors()) {
            response.put(fieldError.getField() , fieldError.getDefaultMessage());
        }

    return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Map<String ,String>> invalidUsernameHandler(InvalidCredentialsException exc){
        Map<String,String> response = new HashMap<>();
        response.put("error" , "Unauthorized");
        response.put("message" , exc.getMessage());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);

    }


    @ExceptionHandler(ExistingUsernameException.class)
    public ResponseEntity<Map<String ,String>> existingUsernameHandler(ExistingUsernameException exc){
        Map<String,String> response = new HashMap<>();
        response.put("error" , "Conflict");
        response.put("message" , exc.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);

    }
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Map<String ,String>> forbiddenExceptionHandler(ForbiddenException exc){
        Map<String,String> response = new HashMap<>();
        response.put("error" , "Forbidden");
        response.put("message" , exc.getMessage());
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,String>> exceptionHandle(){
        Map<String ,String > response = new HashMap<>();
        response.put("error", "Internal server error");
        response.put("message" , "Something went wrong");

        return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);

    }
}

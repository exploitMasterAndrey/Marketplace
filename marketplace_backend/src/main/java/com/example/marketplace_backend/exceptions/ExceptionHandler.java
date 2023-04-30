package com.example.marketplace_backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * Класс обработки исключений
 */
@ControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler({RegisterException.class})
    public ResponseEntity<?> handleRegisterException(){
        return ResponseEntity
                .badRequest()
                .body("Error: Username is already taken!");
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({UsernameNotFoundException.class})
    public ResponseEntity<?> handleLoginFoundException(){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Error: No such user!");
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({ProductNotFoundException.class})
    public ResponseEntity<?> handleProductFoundException(){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Error: No such product!");
    }
}

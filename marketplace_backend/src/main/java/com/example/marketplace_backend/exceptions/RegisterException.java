package com.example.marketplace_backend.exceptions;

/**
 * Класс исключения, обозначающее факт ошибки регистрации
 */
public class RegisterException extends RuntimeException{
    public RegisterException(String message) {
        super(message);
    }
}

package com.example.marketplace_backend.exceptions;

/**
 * Класс исключения, обозначающее факт ненахождения продукта
 */
public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(String message) {
        super(message);
    }
}

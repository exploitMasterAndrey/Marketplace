package com.example.marketplace_backend.dto;

import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Component;

/**
 * Класс модели передаваемых данных пользователя
 */
@Data
@Component
@Getter
public class UserDto {
    /** Идентификатор пользователя*/
    private Long id;
    /** Почта пользователя*/
    private String email;
    /** Имя пользователя*/
    private String username;
    /** Пароль пользователя*/
    private String password;
    /** Аватар пользователя*/
    private String avatar;
}

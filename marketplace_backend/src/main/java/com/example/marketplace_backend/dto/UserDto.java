package com.example.marketplace_backend.dto;

import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Data
@Component
@Getter
public class UserDto {
    private Long id;
    private String email;
    private String username;
    private String password;
    private String avatar;
}

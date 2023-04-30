package com.example.marketplace_backend.controller;

import com.example.marketplace_backend.config.JWTUtil;
import com.example.marketplace_backend.dto.UserDto;
import com.example.marketplace_backend.model.Role;
import com.example.marketplace_backend.model.User;
import com.example.marketplace_backend.service.RoleService;
import com.example.marketplace_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Класс контроллера аутентификации
 * @version 1.0
 */
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    /**Поле слоя бизнес логики */
    private final UserService userService;
    /**Поле менеджера аутендификации */
    private final AuthenticationManager authenticationManager;
    /**Поле утилитного класса JWT */
    private final JWTUtil jwtUtil;
    /**Поле сущности шифрователя паролей */
    private final PasswordEncoder passwordEncoder;

    /**
     * Метод входа пользователя
     * @param userDto
     * @return сгенерированный jwt
     */
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody UserDto userDto) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDto.getEmail(),
                        userDto.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User) authentication.getPrincipal();
        String jwt = jwtUtil.generateToken(user.getUsername());

        Set<String> roles = user.getRoles().stream().map(Role::getAuthority).collect(Collectors.toSet());
        return ResponseEntity.ok(new JWTResponse(jwt, user.getId(), user.getEmail(), user.getUsername(), user.getAvatar(), roles));
    }

    /**
     * Метод регистрации пользователя
     * @param userDto
     * @return ответ с кодом 200
     */
    @PostMapping("/auth/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {
        userService.createCasualUser(userDto);
        return ResponseEntity.ok("User registered successfully!");
    }

    /**
     * Модель ответа jwt токена
     * @param access_token - токен
     * @param id - идентификатор
     * @param email - почта
     * @param name - имя
     * @param avatar - аватарка
     * @param roles - роль
     */
    record JWTResponse(String access_token, Long id, String email, String name, String avatar, Set<String> roles){};
}



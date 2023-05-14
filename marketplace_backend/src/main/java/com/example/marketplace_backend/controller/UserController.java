package com.example.marketplace_backend.controller;

import com.example.marketplace_backend.config.JWTUtil;
import com.example.marketplace_backend.dto.UserDto;
import com.example.marketplace_backend.model.Role;
import com.example.marketplace_backend.model.User;
import com.example.marketplace_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Класс контроллера, обрабатывающий запросы, связанные с пользователями
 * @version 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/profile")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    /**Поле слоя бизнес логики */
    private final UserService userService;
    /**Поле утилитного класса JWT */
    private final JWTUtil jwtUtil;

    /**
     * Метод изменения пользователя
     * @param userDto
     * @return измененный пользователь
     */
    @PutMapping("/update")
    public ResponseEntity<?> updateProfile(@RequestBody UserDto userDto){
        User updated = userService.update(userDto);

        String jwt = jwtUtil.generateToken(updated.getUsername());

        Set<String> roles = updated.getRoles().stream().map(Role::getAuthority).collect(Collectors.toSet());

        return ResponseEntity.ok(new AuthController.JWTResponse(jwt, updated.getId(), updated.getEmail(), updated.getUsername(), updated.getAvatar(), roles));
    }

    /**
     * Метод создания продавца
     * @param userDto
     * @return созданный продавец
     */
    @PostMapping("/createOrUpdateSeller")
    public ResponseEntity<?> createOrUpdateSeller(@RequestBody UserDto userDto){
        if (userDto.getId() != null){
            User updatedSeller = userService.updateSeller(userDto);
            return ResponseEntity.ok(new UserResponse(updatedSeller.getId(), updatedSeller.getEmail(), updatedSeller.getUsername(), updatedSeller.getAvatar()));
        }
        User createdSeller = userService.createSellerUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserResponse(createdSeller.getId(), createdSeller.getEmail(), createdSeller.getUsername(), createdSeller.getAvatar()));
    }

    @GetMapping("/getSellers")
    public ResponseEntity<?> getAllSellers(){
        List<UserResponse> allSellers = userService.getAllSellers().stream()
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getEmail(),
                        user.getUsername(),
                        user.getAvatar())).toList();
        return ResponseEntity.ok(allSellers);
    }

    /**
     * Метод удлания пользователя
     * @param id
     * @return ответ с кодом 200
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        userService.delete(id);
        return ResponseEntity.ok(new DeleteResponse(id));
    }

    /**
     * Модель ответа созданного продавца
     * @param id - идентификатор пользователя
     * @param email - почта
     * @param username - имя пользователя
     * @param avatar - аватар
     */
    record UserResponse(Long id, String email, String username, String avatar){}
    record DeleteResponse(Long id){}
}

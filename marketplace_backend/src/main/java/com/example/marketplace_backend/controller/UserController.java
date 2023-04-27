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

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/profile")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    private final UserService userService;
    private final JWTUtil jwtUtil;
    @PutMapping("/update")
    public ResponseEntity<?> updateProfile(@RequestBody UserDto userDto){
        User updated = userService.update(userDto);

        String jwt = jwtUtil.generateToken(updated.getUsername());

        Set<String> roles = updated.getRoles().stream().map(Role::getAuthority).collect(Collectors.toSet());

        return ResponseEntity.ok(new AuthController.JWTResponse(jwt, updated.getId(), updated.getEmail(), updated.getUsername(), updated.getAvatar(), roles));
    }

    @PostMapping("/createSeller")
    public ResponseEntity<?> createSeller(@RequestBody UserDto userDto){
        User sellerUser = userService.createSellerUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CreatedSellerResponse(sellerUser.getId(), sellerUser.getEmail(), sellerUser.getUsername(), sellerUser.getAvatar()));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        userService.delete(id);
        return ResponseEntity.ok().build();
    }

    record CreatedSellerResponse(Long user_id, String email, String username, String avatar){}
}

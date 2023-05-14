package com.example.marketplace_backend.service;

import com.example.marketplace_backend.dto.UserDto;
import com.example.marketplace_backend.exceptions.RegisterException;
import com.example.marketplace_backend.model.Role;
import com.example.marketplace_backend.model.User;
import com.example.marketplace_backend.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final RoleService roleService;
    private final UserRepo userRepo;
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    public void createCasualUser(UserDto userDto){
        if (existsByUserName(userDto.getEmail())) throw new RegisterException("Email already taken!");
        Role role = roleService.findRoleByName("USER");
        User user = new User(
                userDto.getEmail(),
                userDto.getUsername(),
                passwordEncoder.encode(userDto.getPassword()),
                userDto.getAvatar(),
                Set.of(role)
        );
        userRepo.save(user);
    }

    public User createSellerUser(UserDto userDto){
        if (existsByUserName(userDto.getEmail())) throw new RegisterException("Email already taken!");
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.findRoleByName("USER"));
        roles.add(roleService.findRoleByName("SELLER"));
        String avatar = "https://png.pngtree.com/png-clipart/20200701/original/pngtree-character-default-avatar-png-image_5407167.jpg";
        User user = new User(
                userDto.getEmail(),
                userDto.getUsername(),
                passwordEncoder.encode(userDto.getPassword()),
                userDto.getAvatar().equals("") ? avatar : userDto.getAvatar(),
                roles
        );
        return userRepo.save(user);
    }

    public boolean existsByUserName(String userName){
        Optional<User> byUsername = userRepo.findByUsername(userName);
        return byUsername.isPresent();
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("No user with email = " + email));
    }

    public User update(UserDto userDto){
        User user;
        Optional<User> userOptional = userRepo.findById(userDto.getId());
        if (userOptional.isEmpty()) throw new UsernameNotFoundException("No user with such id!");
        else {
            user = userOptional.get();
            if(userDto.getEmail() != null) user.setEmail(userDto.getEmail());
            if(userDto.getUsername() != null) user.setUsername(userDto.getUsername());
            if(userDto.getPassword() != null) user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            if(userDto.getAvatar() != null) user.setAvatar(userDto.getAvatar());
        }
        return userRepo.save(user);
    }

    public User updateSeller(UserDto userDto){
        Optional<User> userOptional = userRepo.findById(userDto.getId());
        User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("No user with such id!"));
        if(userDto.getUsername() != null) user.setUsername(userDto.getUsername());
        if(userDto.getAvatar() != null) user.setAvatar(userDto.getAvatar());

        return userRepo.save(user);
    }

    public void delete(Long id){
        userRepo.deleteById(id);
    }

    public List<User> getAllSellers(){
        Role role = roleService.findRoleByName("SELLER");
        return userRepo.findUserByRolesContains(role);
    }
}

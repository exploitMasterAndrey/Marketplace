package com.example.marketplace_backend.repository;

import com.example.marketplace_backend.model.Role;
import com.example.marketplace_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String userName);

    Optional<User> findByEmail(String email);

    List<User> findUserByRolesContains(Role role);
}

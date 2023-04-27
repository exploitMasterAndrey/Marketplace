package com.example.marketplace_backend.service;

import com.example.marketplace_backend.model.Role;
import com.example.marketplace_backend.repository.RoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepo roleRepo;

    public Role findRoleByName(String name){
        return roleRepo.findByName(name);
    }
}
